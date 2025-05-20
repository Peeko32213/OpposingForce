package com.unusualmodding.opposing_force.client.particles;

import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector4f;

import java.util.*;

public class LightningBoltData {
    private final Random random;
    private final BoltRenderInfo renderInfo;
    private final Vec3 start;
    private final Vec3 end;
    private final int segments;
    private int count;
    private float size;
    private int lifespan;
    private SpawnFunction spawnFunction;
    private FadeFunction fadeFunction;

    public LightningBoltData(Vec3 start, Vec3 end) {
        this(BoltRenderInfo.DEFAULT, start, end, (int)Math.sqrt(start.distanceTo(end) * 100.0));
    }

    public LightningBoltData(BoltRenderInfo info, Vec3 start, Vec3 end, int segments) {
        this.random = new Random();
        this.count = 1;
        this.size = 0.1F;
        this.lifespan = 30;
        this.spawnFunction = SpawnFunction.delay(60.0F);
        this.fadeFunction = FadeFunction.fade(0.5F);
        this.renderInfo = info;
        this.start = start;
        this.end = end;
        this.segments = segments;
    }

    public LightningBoltData count(int count) {
        this.count = count;
        return this;
    }

    public LightningBoltData size(float size) {
        this.size = size;
        return this;
    }

    public LightningBoltData spawn(SpawnFunction spawnFunction) {
        this.spawnFunction = spawnFunction;
        return this;
    }

    public LightningBoltData fade(FadeFunction fadeFunction) {
        this.fadeFunction = fadeFunction;
        return this;
    }

    public LightningBoltData lifespan(int lifespan) {
        this.lifespan = lifespan;
        return this;
    }

    public int getLifespan() {
        return this.lifespan;
    }

    public SpawnFunction getSpawnFunction() {
        return this.spawnFunction;
    }

    public FadeFunction getFadeFunction() {
        return this.fadeFunction;
    }

    public Vector4f getColor() {
        return this.renderInfo.color;
    }

    public List<BoltQuads> generate() {
        List<BoltQuads> quads = new ArrayList();
        Vec3 diff = this.end.subtract(this.start);
        float totalDistance = (float)diff.length();

        for(int i = 0; i < this.count; ++i) {
            LinkedList<BoltInstructions> drawQueue = new LinkedList();
            drawQueue.add(new BoltInstructions(this.start, 0.0F, new Vec3(0.0, 0.0, 0.0), (QuadCache)null, false));

            while(!drawQueue.isEmpty()) {
                BoltInstructions data = (BoltInstructions)drawQueue.poll();
                Vec3 perpendicularDist = data.perpendicularDist;
                float progress = data.progress + 1.0F / (float)this.segments * (1.0F - this.renderInfo.parallelNoise + this.random.nextFloat() * this.renderInfo.parallelNoise * 2.0F);
                Vec3 segmentEnd;
                float boltSize;
                if (progress >= 1.0F) {
                    segmentEnd = this.end;
                } else {
                    boltSize = this.renderInfo.spreadFunction.getMaxSpread(progress);
                    float maxDiff = this.renderInfo.spreadFactor * boltSize * totalDistance * this.renderInfo.randomFunction.getRandom(this.random);
                    Vec3 randVec = findRandomOrthogonalVector(diff, this.random);
                    perpendicularDist = this.renderInfo.segmentSpreader.getSegmentAdd(perpendicularDist, randVec, maxDiff, boltSize, progress);
                    segmentEnd = this.start.add(diff.scale((double)progress)).add(perpendicularDist);
                }

                boltSize = this.size * (0.5F + (1.0F - progress) * 0.5F);
                Pair<BoltQuads, QuadCache> quadData = this.createQuads(data.cache, data.start, segmentEnd, boltSize);
                quads.add((BoltQuads)quadData.getLeft());
                if (segmentEnd == this.end) {

                    break;
                }

                if (!data.isBranch) {
                    drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, (QuadCache)quadData.getRight(), false));
                } else if (this.random.nextFloat() < this.renderInfo.branchContinuationFactor) {
                    drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, (QuadCache)quadData.getRight(), true));
                }

                while(this.random.nextFloat() < this.renderInfo.branchInitiationFactor * (1.0F - progress)) {
                    drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, (QuadCache)quadData.getRight(), true));
                }
            }
        }

        return quads;
    }

    private static Vec3 findRandomOrthogonalVector(Vec3 vec, Random rand) {
        Vec3 newVec = new Vec3(-0.5 + rand.nextDouble(), -0.5 + rand.nextDouble(), -0.5 + rand.nextDouble());
        return vec.cross(newVec).normalize();
    }

    private Pair<BoltQuads, QuadCache> createQuads(QuadCache cache, Vec3 startPos, Vec3 end, float size) {
        Vec3 diff = end.subtract(startPos);
        Vec3 rightAdd = diff.cross(new Vec3(0.5, 0.5, 0.5)).normalize().scale((double)size);
        Vec3 backAdd = diff.cross(rightAdd).normalize().scale((double)size);
        Vec3 rightAddSplit = rightAdd.scale(0.5);
        Vec3 start = cache != null ? cache.prevEnd : startPos;
        Vec3 startRight = cache != null ? cache.prevEndRight : start.add(rightAdd);
        Vec3 startBack = cache != null ? cache.prevEndBack : start.add(rightAddSplit).add(backAdd);
        Vec3 endRight = end.add(rightAdd);
        Vec3 endBack = end.add(rightAddSplit).add(backAdd);
        BoltQuads quads = new BoltQuads();
        quads.addQuad(start, end, endRight, startRight);
        quads.addQuad(startRight, endRight, end, start);
        quads.addQuad(startRight, endRight, endBack, startBack);
        quads.addQuad(startBack, endBack, endRight, startRight);
        return Pair.of(quads, new QuadCache(end, endRight, endBack));
    }

    public static class BoltRenderInfo {
        public static final BoltRenderInfo DEFAULT = new BoltRenderInfo();
        public static final BoltRenderInfo ELECTRICITY = electricity();
        private float parallelNoise = 0.1F;
        private float spreadFactor = 0.1F;
        private float branchInitiationFactor = 0.0F;
        private float branchContinuationFactor = 0.0F;
        private Vector4f color = new Vector4f(0.45F, 0.45F, 0.5F, 0.8F);
        private RandomFunction randomFunction;
        private SpreadFunction spreadFunction;
        private SegmentSpreader segmentSpreader;

        public static BoltRenderInfo electricity() {
            return new BoltRenderInfo(0.5F, 0.25F, 0.25F, 0.15F, new Vector4f(0.7F, 0.45F, 0.89F, 0.8F), 0.8F);
        }

        public BoltRenderInfo() {
            this.randomFunction = RandomFunction.GAUSSIAN;
            this.spreadFunction = SpreadFunction.SINE;
            this.segmentSpreader = SegmentSpreader.NO_MEMORY;
        }

        public BoltRenderInfo(float parallelNoise, float spreadFactor, float branchInitiationFactor, float branchContinuationFactor, Vector4f color, float closeness) {
            this.randomFunction = RandomFunction.GAUSSIAN;
            this.spreadFunction = SpreadFunction.SINE;
            this.segmentSpreader = SegmentSpreader.NO_MEMORY;
            this.parallelNoise = parallelNoise;
            this.spreadFactor = spreadFactor;
            this.branchInitiationFactor = branchInitiationFactor;
            this.branchContinuationFactor = branchContinuationFactor;
            this.color = color;
            this.segmentSpreader = SegmentSpreader.memory(closeness);
        }
    }

    public interface SpawnFunction {
        SpawnFunction NO_DELAY = (rand) -> {
            return Pair.of(0.0F, 0.0F);
        };
        SpawnFunction CONSECUTIVE = new SpawnFunction() {
            public Pair<Float, Float> getSpawnDelayBounds(Random rand) {
                return Pair.of(0.0F, 0.0F);
            }

            public boolean isConsecutive() {
                return true;
            }
        };

        static SpawnFunction delay(float delay) {
            return (rand) -> {
                return Pair.of(delay, delay);
            };
        }

        static SpawnFunction noise(float delay, float noise) {
            return (rand) -> {
                return Pair.of(delay - noise, delay + noise);
            };
        }

        Pair<Float, Float> getSpawnDelayBounds(Random var1);

        default float getSpawnDelay(Random rand) {
            Pair<Float, Float> bounds = this.getSpawnDelayBounds(rand);
            return (Float)bounds.getLeft() + ((Float)bounds.getRight() - (Float)bounds.getLeft()) * rand.nextFloat();
        }

        default boolean isConsecutive() {
            return false;
        }
    }

    public interface FadeFunction {
        FadeFunction NONE = (totalBolts, lifeScale) -> {
            return Pair.of(0, totalBolts);
        };

        static FadeFunction fade(float fade) {
            return (totalBolts, lifeScale) -> {
                int start = lifeScale > 1.0F - fade ? (int)((float)totalBolts * (lifeScale - (1.0F - fade)) / fade) : 0;
                int end = lifeScale < fade ? (int)((float)totalBolts * (lifeScale / fade)) : totalBolts;
                return Pair.of(start, end);
            };
        }

        Pair<Integer, Integer> getRenderBounds(int var1, float var2);
    }

    protected static class BoltInstructions {
        private final Vec3 start;
        private final Vec3 perpendicularDist;
        private final QuadCache cache;
        private final float progress;
        private final boolean isBranch;

        private BoltInstructions(Vec3 start, float progress, Vec3 perpendicularDist, QuadCache cache, boolean isBranch) {
            this.start = start;
            this.perpendicularDist = perpendicularDist;
            this.progress = progress;
            this.cache = cache;
            this.isBranch = isBranch;
        }
    }

    private static class QuadCache {
        private final Vec3 prevEnd;
        private final Vec3 prevEndRight;
        private final Vec3 prevEndBack;

        private QuadCache(Vec3 prevEnd, Vec3 prevEndRight, Vec3 prevEndBack) {
            this.prevEnd = prevEnd;
            this.prevEndRight = prevEndRight;
            this.prevEndBack = prevEndBack;
        }
    }

    public interface SpreadFunction {
        SpreadFunction LINEAR_ASCENT = (progress) -> {
            return progress;
        };
        SpreadFunction LINEAR_ASCENT_DESCENT = (progress) -> {
            return (progress - Math.max(0.0F, 2.0F * progress - 1.0F)) / 0.5F;
        };
        SpreadFunction SINE = (progress) -> {
            return (float)Math.sin(Math.PI * (double)progress);
        };

        float getMaxSpread(float var1);
    }

    public interface RandomFunction {
        RandomFunction UNIFORM = Random::nextFloat;
        RandomFunction GAUSSIAN = (rand) -> {
            return (float)rand.nextGaussian();
        };

        float getRandom(Random var1);
    }

    public interface SegmentSpreader {
        SegmentSpreader NO_MEMORY = (perpendicularDist, randVec, maxDiff, scale, progress) -> {
            return randVec.scale((double)maxDiff);
        };

        static SegmentSpreader memory(float memoryFactor) {
            return (perpendicularDist, randVec, maxDiff, spreadScale, progress) -> {
                float nextDiff = maxDiff * (1.0F - memoryFactor);
                Vec3 cur = randVec.scale((double)nextDiff);
                if (progress > 0.5F) {
                    cur = cur.add(perpendicularDist.scale((double)(-1.0F * (1.0F - spreadScale))));
                }

                return perpendicularDist.add(cur);
            };
        }

        Vec3 getSegmentAdd(Vec3 var1, Vec3 var2, float var3, float var4, float var5);
    }

    public class BoltQuads {
        private final List<Vec3> vecs = new ArrayList();

        public BoltQuads() {
        }

        protected void addQuad(Vec3... quadVecs) {
            this.vecs.addAll(Arrays.asList(quadVecs));
        }

        public List<Vec3> getVecs() {
            return this.vecs;
        }
    }
}
