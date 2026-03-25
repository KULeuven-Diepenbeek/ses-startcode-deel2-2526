package ses.candycrush;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import ses.candycrush.board.Position;
import ses.candycrush.model.Candy;
import ses.candycrush.model.CandyCrushGame;
import ses.candycrush.model.Util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Assignment04_Recursion_Tests {

    @Test
    public void test_clearMatch_isRecursive() throws NoSuchMethodException, IOException {
        Method m = CandyCrushGame.class.getMethod("clearMatch", List.class);
        assertThat(isRecursive(m)).withFailMessage("Jouw clearMatch-methode lijkt geen recursieve oproep te bevatten.").isTrue();
    }

    @Test
    public void test_fallDownTo_isRecursive() throws NoSuchMethodException, IOException {
        Method m = CandyCrushGame.class.getMethod("fallDownTo", Position.class);
        assertThat(isRecursive(m)).withFailMessage("Jouw fallDownTo-methode lijkt geen recursieve oproep te bevatten.").isTrue();
    }

    @Test
    public void test_updateBoard_isRecursive() throws NoSuchMethodException, IOException {
        Method m = CandyCrushGame.class.getMethod("updateBoard");
        assertThat(isRecursive(m)).withFailMessage("Jouw updateBoard-methode lijkt geen recursieve oproep te bevatten.").isTrue();
    }

    @Test
    public void test_fallDownTo_example1() {
        var gameModel = Util.createBoardFromString("""
                *
                .
                *
                *
                .
                .
                *""");
        var size = gameModel.getSize();

        var expectedModel = Util.createBoardFromString("""
                .
                .
                *
                *
                *
                .
                *""");

        gameModel.fallDownTo(new Position(4, 0, size));
        var actual = size.positions().stream().map(gameModel::getCandyAt).toList();
        var expected = size.positions().stream().map(expectedModel::getCandyAt).toList();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test_fallDownTo_example2() {
        var gameModel = Util.createBoardFromString("""
                .
                *
                .
                *
                *
                .
                *""");
        var size = gameModel.getSize();

        var expectedModel = Util.createBoardFromString("""
                .
                .
                *
                *
                *
                .
                *""");

        gameModel.fallDownTo(new Position(4, 0, size));
        var actual = size.positions().stream().map(gameModel::getCandyAt).toList();
        var expected = size.positions().stream().map(expectedModel::getCandyAt).toList();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test_updateBoard_works() {
        var gameAtStart = Util.createBoardFromString("""
                #@@.#
                @***#
                o##@#
                ###*#""");

        var gameAfterOneBoardUpdate = Util.createBoardFromString("""
                .....
                .....
                #....
                o##*.""");
        var size = gameAtStart.getSize();

        gameAtStart.updateBoard();

        var actual = candies(gameAtStart);
        var expected = candies(gameAfterOneBoardUpdate);
        assertThat(actual).isEqualTo(expected);
    }

    private static List<Candy> candies(CandyCrushGame game) {
        return game.getSize().positions().stream()
                .map(game::getCandyAt)
                .toList();
    }



    /** You do not need to look at, understand, or modify the code below **/

    /**
     * Returns true if the given instance method contains
     * at least one call to itself (i.e. is directly recursive).
     */
    public static boolean isRecursive(Method method) throws IOException {
        Class<?> clazz = method.getDeclaringClass();
        // internal name: replace dots with slashes
        String internalName = clazz.getName().replace('.', '/');

        // load the raw .class bytes
        InputStream in = clazz.getClassLoader().getResourceAsStream(internalName + ".class");
        if (in == null) {
            throw new IOException("Cannot load bytecode for " + clazz.getName());
        }

        ClassReader reader = new ClassReader(in);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);

        // compute the ASM descriptor for our Method
        String methodDesc = Type.getMethodDescriptor(method);
        String methodName = method.getName();

        // find the matching MethodNode
        @SuppressWarnings("unchecked")
        List<MethodNode> methods = classNode.methods;
        for (MethodNode mn : methods) {
            if (mn.name.equals(methodName) && mn.desc.equals(methodDesc)) {
                // scan its instructions for self‐invocation
                for (AbstractInsnNode insn : mn.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode call = (MethodInsnNode) insn;
                        // instance calls are INVOKEVIRTUAL or INVOKESPECIAL
                        if ((call.getOpcode() == Opcodes.INVOKEVIRTUAL ||
                                call.getOpcode() == Opcodes.INVOKESPECIAL)
                                // same owner, name, and descriptor = self‐call
                                && call.owner.equals(internalName)
                                && call.name.equals(methodName)
                                && call.desc.equals(methodDesc)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
