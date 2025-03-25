package net.homak.homakmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulForceFieldItem extends Item {

    private static final int COOLDOWN_TICKS = 5 * 20; // cooldown seconds
    private static final double RADIUS = 5.0; // block radius

    public SoulForceFieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            activateForceField(world, user);
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(stack);
    }

    private void activateForceField(World world, PlayerEntity user) {
        Vec3d center = user.getPos();
        Box area = new Box(center.add(-RADIUS, -RADIUS, -RADIUS), center.add(RADIUS, RADIUS, RADIUS));

        for (Entity entity : world.getEntitiesByClass(Entity.class, area, e -> e != user)) {
            if (entity instanceof LivingEntity) {
                Vec3d direction = entity.getPos().subtract(center).normalize();
                entity.setVelocity(direction.multiply(3.0));
            }
        }

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z, 2000, RADIUS, RADIUS, RADIUS, 0.1);
        }
    }
}