/*
 * AccessFlag.java    11:11, June 20, 2015
 *
 * Copyright 2015, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Amos Shi
 */
public enum AccessFlag {

    /**
     * Declared <code>public</code>; may be accessed from outside its package.
     */
    ACC_PUBLIC(0x0001, "public"),
    /**
     * Declared <code>private</code>; usable only within the defining class.
     */
    ACC_PRIVATE(0x0002, "private"),
    /**
     * Declared <code>protected</code>; may be accessed within subclasses.
     */
    ACC_PROTECTED(0x0004, "protected"),
    /**
     * Declared <code>static</code>.
     */
    ACC_STATIC(0x0008, "static"),
    /**
     * Declared <code>final</code>; no subclasses allowed.
     */
    ACC_FINAL(0x0010, "final"),
    /**
     * Treat superclass methods specially when invoked by the
     * <code>invokespecial</code> instruction.
     */
    ACC_SUPER(0x0020, "super"),
    /**
     * Declared <code>synchronized</code>; invocation is wrapped by a monitor
     * use.
     */
    ACC_SYNCHRONIZED(0x0020, "synchronized"),
    /**
     * Declared <code>volatile</code>; cannot be cached.
     */
    ACC_VOLATILE(0x0040, "volatile"),
    /**
     * A bridge method, generated by the compiler.
     */
    ACC_BRIDGE(0x0040, "(ACC_BRIDGE)"),
    /**
     * Declared <code>transient</code>; not written or read by a persistent
     * object manager.
     */
    ACC_TRANSIENT(0x0080, "transient"),
    /**
     * Declared with variable number of arguments.
     */
    ACC_VARARGS(0x0080, "(ACC_VARARGS)"),
    /**
     * Declared <code>native</code>; implemented in a language other than Java.
     */
    ACC_NATIVE(0x0100, "native"),
    /**
     * Is an interface, not a class.
     */
    ACC_INTERFACE(0x0200, "interface"),
    /**
     * Declared <code>abstract</code>; must not be instantiated.
     */
    ACC_ABSTRACT(0x0400, "abstract"),
    /**
     * Declared <code>strictfp</code>; floating-point mode is FP-strict.
     */
    ACC_STRICT(0x0800, "strictfp"),
    /**
     * Declared <code>synthetic</code>; not present in the source code.
     */
    ACC_SYNTHETIC(0x1000, "(ACC_SYNTHETIC)"),
    /**
     * Declared as an annotation type.
     */
    ACC_ANNOTATION(0x2000, "@interface"),
    /**
     * Declared as an <code>enum</code> type.
     */
    ACC_ENUM(0x4000, "enum"),
    /**
     * Is a module, not a class or interface.
     */
    ACC_MODULE(0x8000, "module"),
    /**
     * Indicates that the formal parameter was implicitly declared in source
     * code, according to the specification of the language in which the source
     * code was written (JLS §13.1). (The formal parameter is mandated by a
     * language specification, so all compilers for the language must emit it.)
     */
    ACC_MANDATED(0x8000, "ACC_MANDATED");

    /**
     * Binary value in the {@link ClassFile}.
     */
    public final int value;

    /**
     * Modifier in the java source file. Some modifier does not exist in the
     * source file but generated by compiler.
     */
    public final String modifier;

    /**
     * Modifiers for {@link ClassFile}.
     */
    public static final List<AccessFlag> ForClass = Collections.synchronizedList(new ArrayList<AccessFlag>());
    /**
     * Modifiers for {@link FieldInfo}.
     */
    public static final List<AccessFlag> ForField = Collections.synchronizedList(new ArrayList<AccessFlag>());
    /**
     * Modifiers for {@link MethodInfo}.
     */
    public static final List<AccessFlag> ForMethod = Collections.synchronizedList(new ArrayList<AccessFlag>());
    /**
     * Modifiers for {@link org.freeinternals.format.classfile.attribute.AttributeInnerClasses}.
     */
    public static final List<AccessFlag> ForInnerClass = Collections.synchronizedList(new ArrayList<AccessFlag>());
    /**
     * Modifiers for {@link org.freeinternals.format.classfile.attribute.AttributeMethodParameters}.
     */
    public static final List<AccessFlag> ForMethodParameters = Collections.synchronizedList(new ArrayList<AccessFlag>());

    static {
        // Access flags for a Class File
        AccessFlag.ForClass.add(AccessFlag.ACC_PUBLIC);
        AccessFlag.ForClass.add(AccessFlag.ACC_FINAL);
        AccessFlag.ForClass.add(AccessFlag.ACC_SUPER);
        AccessFlag.ForClass.add(AccessFlag.ACC_INTERFACE);
        AccessFlag.ForClass.add(AccessFlag.ACC_ABSTRACT);
        AccessFlag.ForClass.add(AccessFlag.ACC_SYNTHETIC);
        AccessFlag.ForClass.add(AccessFlag.ACC_ANNOTATION);
        AccessFlag.ForClass.add(AccessFlag.ACC_ENUM);
        AccessFlag.ForClass.add(AccessFlag.ACC_MODULE);

        // Access flags for a Field
        AccessFlag.ForField.add(AccessFlag.ACC_PUBLIC);
        AccessFlag.ForField.add(AccessFlag.ACC_PRIVATE);
        AccessFlag.ForField.add(AccessFlag.ACC_PROTECTED);
        AccessFlag.ForField.add(AccessFlag.ACC_STATIC);
        AccessFlag.ForField.add(AccessFlag.ACC_FINAL);
        AccessFlag.ForField.add(AccessFlag.ACC_VOLATILE);
        AccessFlag.ForField.add(AccessFlag.ACC_TRANSIENT);
        AccessFlag.ForField.add(AccessFlag.ACC_SYNTHETIC);
        AccessFlag.ForField.add(AccessFlag.ACC_ENUM);

        // Access flags for a Method
        AccessFlag.ForMethod.add(AccessFlag.ACC_PUBLIC);
        AccessFlag.ForMethod.add(AccessFlag.ACC_PRIVATE);
        AccessFlag.ForMethod.add(AccessFlag.ACC_PROTECTED);
        AccessFlag.ForMethod.add(AccessFlag.ACC_STATIC);
        AccessFlag.ForMethod.add(AccessFlag.ACC_FINAL);
        AccessFlag.ForMethod.add(AccessFlag.ACC_SYNCHRONIZED);
        AccessFlag.ForMethod.add(AccessFlag.ACC_BRIDGE);
        AccessFlag.ForMethod.add(AccessFlag.ACC_VARARGS);
        AccessFlag.ForMethod.add(AccessFlag.ACC_NATIVE);
        AccessFlag.ForMethod.add(AccessFlag.ACC_ABSTRACT);
        AccessFlag.ForMethod.add(AccessFlag.ACC_STRICT);
        AccessFlag.ForMethod.add(AccessFlag.ACC_SYNTHETIC);

        // Access flags for an Inner Class
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_PUBLIC);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_PRIVATE);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_PROTECTED);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_STATIC);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_FINAL);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_INTERFACE);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_ABSTRACT);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_SYNTHETIC);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_ANNOTATION);
        AccessFlag.ForInnerClass.add(AccessFlag.ACC_ENUM);

        //Access flags for an MethodParameters 
        AccessFlag.ForMethodParameters.add(AccessFlag.ACC_FINAL);
        AccessFlag.ForMethodParameters.add(AccessFlag.ACC_SYNTHETIC);
        AccessFlag.ForMethodParameters.add(AccessFlag.ACC_MANDATED);
    }

    /**
     * @param i Value in the Class file
     * @param m Modifier in the java source file
     */
    AccessFlag(int i, String m) {
        this.value = i;
        this.modifier = m;
    }

    /**
     * Check if the the <code>accFlags</code> matches the access flag or not.
     *
     * @param accFlags the access flags value
     * @return <code>true</code> if the access flag matches the
     * <code>accFlags</code>, else <code>false</code>
     */
    public boolean match(int accFlags) {
        return (accFlags & this.value) > 0;
    }

    /**
     * Get the modifiers text for a {@link ClassFile}.
     *
     * @param value Value in the Class file
     * @return Modifier text
     */
    public static String getClassModifier(int value) {
        return getModifier(value, AccessFlag.ForClass);
    }

    /**
     * Get the modifiers text for a {@link FieldInfo}.
     *
     * @param value Value in the Class file
     * @return Modifier text
     */
    public static String getFieldModifier(int value) {
        return getModifier(value, AccessFlag.ForField);
    }

    /**
     * Get the modifiers text for a {@link MethodInfo}.
     *
     * @param value Value in the Class file
     * @return Modifier text
     */
    public static String getMethodModifier(int value) {
        return getModifier(value, AccessFlag.ForMethod);
    }

    /**
     * Get the modifiers text for a {@link org.freeinternals.format.classfile.attribute.AttributeInnerClasses}.
     *
     * @param value Value in the Class file
     * @return Modifier text
     */
    public static String getInnerClassModifier(int value) {
        return getModifier(value, AccessFlag.ForInnerClass);
    }

    /**
     * Get the modifiers text for a {@link org.freeinternals.format.classfile.attribute.AttributeMethodParameters}.
     *
     * @param value Value in the Class file
     * @return Modifier text
     */
    public static String getMethodParametersModifier(int value) {
        return getModifier(value, AccessFlag.ForMethodParameters);
    }

    private static String getModifier(int value, List<AccessFlag> list) {
        final StringBuilder sb = new StringBuilder(25);

        for (AccessFlag af : list) {
            if (af.match(value)) {
                sb.append(af.modifier);
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}
