package org.smartmvc.tools;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * asm工具类
 * @author ZHANGYUKUN
 *
 */
public class ParameterNameUtils {

    public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        
        if (parameterTypes == null || parameterTypes.length == 0) {
            return null;
        }
        
        final Type[] types = new Type[parameterTypes.length];
        
        for (int i = 0; i < parameterTypes.length; i++) {
            types[i] = Type.getType(parameterTypes[i]);
        }
        
        final String[] parameterNames = new String[parameterTypes.length];

        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        className = className.substring(lastDotIndex + 1) + ".class";
        InputStream is = clazz.getResourceAsStream(className);
        
        
        try {
            ClassReader classReader = new ClassReader(is);
            
            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
            	
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                	
					Type[] argumentTypes = Type.getArgumentTypes(desc);
					
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                        return null;
                    }
                    
                    return new MethodVisitor(Opcodes.ASM4) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {

                        	if (Modifier.isStatic(method.getModifiers())) {
                                parameterNames[index] = name;
                            }else if (index > 0 && index <= parameterNames.length) {
                                parameterNames[index - 1] = name;
                            }
                        }
                    };

                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }

}