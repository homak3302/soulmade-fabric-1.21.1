package net.homak.homakmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulCleaverItem extends AxeItem {

    private static final int FIRE_DURATION = 5; // Fire damage
    private static final int BLINDNESS_DURATION = 15; // Effect Duration (in ticks)
    private static final int COOLDOWN_TICKS = 5 * 20; // cooldown seconds
    private static final double RADIUS = 5.0; // block radius

    public SoulCleaverItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    // Force Field: Applies a force effect to all entities in the radius
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

        // Iterate over all entities in the area
        for (Entity entity : world.getEntitiesByClass(Entity.class, area, e -> e != user)) {
            if (entity instanceof LivingEntity) {
                Vec3d direction = entity.getPos().subtract(center).normalize();
                entity.setVelocity(direction.multiply(3.0));
                applyEffectsToEntity((LivingEntity) entity);
            }
        }

        // Particles
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, center.x, center.y, center.z, 2000, RADIUS, RADIUS, RADIUS, 0.1);
        }
    }

    private void applyEffectsToEntity(LivingEntity entity) {

        entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(StatusEffects.BLINDNESS, BLINDNESS_DURATION, 1));

        entity.setOnFireFor(FIRE_DURATION);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            applyEffectsToEntity(target);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
