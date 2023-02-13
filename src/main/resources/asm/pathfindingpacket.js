// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'pathfindingpacket': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.network.protocol.game.DebugPackets',
                'methodName': ASM.mapMethod('m_133703_'), // sendPathFindingPacket
                'methodDesc': '(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/level/pathfinder/Path;F)V'
            },
            'transformer': function (methodNode) { // MethodNode
                var instructions = methodNode.instructions; // InsnList
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.RETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new VarInsnNode(Opcodes.ALOAD, 2),
                        new VarInsnNode(Opcodes.FLOAD, 3),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'debugtools/ASMHooks',
                            'pathFindingPacket',
                            '(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/level/pathfinder/Path;F)V',
                            false
                        )
                    )
                );
                return methodNode;
            }
        }
    }
}
