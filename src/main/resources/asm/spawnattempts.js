// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

/**
 * This coremod is for
 * @returns {{}}
 */
// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'spawncosts': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.world.level.NaturalSpawner$SpawnState',
                'methodName': ASM.mapMethod('m_47127_'), // canSpawn
                'methodDesc': '(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/ChunkAccess;)Z'
            },
            'transformer': function (methodNode) {
                var instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstructionBefore(methodNode, Opcodes.ICONST_0, 0), // Run the code if the method is about to return false
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new VarInsnNode(Opcodes.ALOAD, 2),
                        new VarInsnNode(Opcodes.ALOAD, 3),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'debugtools/ASMHooks',
                            'spawnCostInfoPacket',
                            '(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/ChunkAccess;)V',
                            false
                        )
                    )
                );
                return methodNode;
            }
        }
    }
}