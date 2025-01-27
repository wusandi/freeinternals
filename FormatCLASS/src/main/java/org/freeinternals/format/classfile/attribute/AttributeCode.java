/*
 * AttributeCode.java    5:09 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile.attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.commonlib.core.FileComponent;
import org.freeinternals.commonlib.core.PosDataInputStream;
import org.freeinternals.commonlib.ui.JTreeNodeFileComponent;
import org.freeinternals.commonlib.core.FileFormatException;
import org.freeinternals.format.classfile.ClassFile;
import org.freeinternals.format.classfile.JavaSEVersion;
import org.freeinternals.format.classfile.constant.CPInfo;
import org.freeinternals.format.classfile.Opcode;
import org.freeinternals.format.classfile.u2;
import org.freeinternals.format.classfile.u4;

/**
 * The class for the {@code Code} attribute. The {@code Code} attribute has the
 * following format:
 *
 * <pre>
 *    Code_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *
 *        u2 max_stack;
 *        u2 max_locals;
 *        u4 code_length;
 *        u1 code[code_length];
 *        u2 exception_table_length;
 *        {
 *                u2 start_pc;
 *                u2 end_pc;
 *                u2 handler_pc;
 *                u2 catch_type;
 *        } exception_table[exception_table_length];
 *        u2 attributes_count;
 *        attribute_info attributes[attributes_count];
 *    }
 * </pre>
 *
 *
 * @author Amos Shi
 * @since Java 1.0.2
 * @see
 * <a href="https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-4.html#jvms-4.7.3">
 * VM Spec: The Code Attribute
 * </a>
 */
public class AttributeCode extends AttributeInfo {

    public static final String ATTRIBUTE_CODE_NODE = "code";

    public transient final u2 max_stack;
    public transient final u2 max_locals;
    public transient final u4 code_length;
    private transient final byte[] code;
    public transient final u2 exception_table_length;
    public transient ExceptionTable[] exceptionTable;
    public transient final u2 attributes_count;
    public transient AttributeInfo[] attributes;

    AttributeCode(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream, final CPInfo[] cp) throws IOException, FileFormatException {
        super(nameIndex, type, posDataInputStream, ClassFile.Version.Format_45_3, JavaSEVersion.Version_1_0_2);

        int i;

        this.max_stack = new u2(posDataInputStream);
        this.max_locals = new u2(posDataInputStream);
        this.code_length = new u4(posDataInputStream);
        this.code = new byte[this.code_length.value];
        int readBytes = posDataInputStream.read(this.code);
        if (readBytes != this.code_length.value) {
            throw new IOException(String.format("Failed to read %d bytes, actual bytes read %d", this.code_length.value, readBytes));
        }

        this.exception_table_length = new u2(posDataInputStream);
        if (this.exception_table_length.value > 0) {
            this.exceptionTable = new ExceptionTable[this.exception_table_length.value];
            for (i = 0; i < this.exception_table_length.value; i++) {
                this.exceptionTable[i] = new ExceptionTable(posDataInputStream);
            }
        }

        this.attributes_count = new u2(posDataInputStream);
        if (this.attributes_count.value > 0) {
            this.attributes = new AttributeInfo[this.attributes_count.value];
            for (i = 0; i < this.attributes_count.value; i++) {
                this.attributes[i] = AttributeInfo.parse(posDataInputStream, cp);
            }

        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code code}.
     *
     * @return The value of {@code code}
     */
    public byte[] getCode() {
        return this.code.clone();
    }

    /**
     * Get the {@link #code} parse result. This method will return an empty list
     * if {@link #code} is <code>null</code>.
     *
     * @return Parsed {@link Opcode} list
     */
    public List<Opcode.InstructionParsed> parseCode() {
        if (this.code != null && this.code.length > 0) {
            return Opcode.parseCode(this.code);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get the value of {@code exception_table}[{@code index}].
     *
     * @param index Index of the exception table
     * @return The value of {@code exception_table}[{@code index}]
     */
    public ExceptionTable getExceptionTable(final int index) {
        ExceptionTable et = null;
        if (this.exceptionTable != null) {
            et = this.exceptionTable[index];
        }
        return et;
    }

    /**
     * Get the value of {@code attributes}[{@code index}].
     *
     * @param index Zero-based index of the attributes
     * @return The value of {@code attributes}[{@code index}]
     */
    public AttributeInfo getAttribute(final int index) {
        return this.attributes[index];
    }

    @Override
    public void generateTreeNode(DefaultMutableTreeNode parentNode, final ClassFile classFile) {
        int i;
        final int codeLength = this.code_length.value;
        DefaultMutableTreeNode treeNodeExceptionTable;
        DefaultMutableTreeNode treeNodeExceptionTableItem;
        final int attrCount = this.attributes_count.value;
        DefaultMutableTreeNode treeNodeAttribute;
        DefaultMutableTreeNode treeNodeAttributeItem;

        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                super.startPos + 6,
                2,
                "max_stack: " + this.max_stack.value
        )));
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                super.startPos + 8,
                2,
                "max_locals: " + this.max_locals.value
        )));
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                super.startPos + 10,
                4,
                "code_length: " + this.code_length.value
        )));
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                super.startPos + 14,
                codeLength,
                ATTRIBUTE_CODE_NODE
        )));
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                super.startPos + 14 + codeLength,
                2,
                "exception_table_length: " + this.exception_table_length.value
        )));

        // Add exception table
        if (this.exception_table_length.value > 0) {
            treeNodeExceptionTable = new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                    super.startPos + 14 + codeLength + 2,
                    ExceptionTable.LENGTH * this.exception_table_length.value,
                    "exception_table[" + this.exception_table_length.value + "]"
            ));

            AttributeCode.ExceptionTable et;
            for (i = 0; i < this.exception_table_length.value; i++) {
                et = this.getExceptionTable(i);

                treeNodeExceptionTableItem = new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                        et.getStartPos(),
                        et.getLength(),
                        String.format("exception_table [%d]", i)
                ));
                this.generateSubnode(treeNodeExceptionTableItem, et, classFile);
                treeNodeExceptionTable.add(treeNodeExceptionTableItem);
            }

            parentNode.add(treeNodeExceptionTable);
        }

        // Add attributes
        final int attrStartPos = super.startPos + 14 + codeLength + 2 + this.exception_table_length.value * ExceptionTable.LENGTH;
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                attrStartPos,
                2,
                "attributes_count: " + attrCount
        )));
        if (attrCount > 0) {
            int attrLength = 0;
            for (AttributeInfo codeAttr : this.attributes) {
                attrLength += codeAttr.getLength();
            }

            treeNodeAttribute = new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                    attrStartPos + 2,
                    attrLength,
                    "attributes[" + attrCount + "]"
            ));

            for (i = 0; i < attrCount; i++) {
                AttributeInfo attr = this.getAttribute(i);
                treeNodeAttributeItem = new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                        attr.getStartPos(),
                        attr.getLength(),
                        (i + 1) + ". " + attr.getName()
                ));
                AttributeInfo.generateTreeNode(treeNodeAttributeItem, attr, classFile);

                treeNodeAttribute.add(treeNodeAttributeItem);
            }

            parentNode.add(treeNodeAttribute);
        }
    }

    private void generateSubnode(final DefaultMutableTreeNode rootNode, final AttributeCode.ExceptionTable et, final ClassFile classFile) {
        if (et == null) {
            return;
        }

        final int startPosMoving = et.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                startPosMoving,
                2,
                "start_pc: " + et.start_pc.value
        )));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                startPosMoving + 2,
                2,
                "end_pc: " + et.end_pc.value
        )));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                startPosMoving + 4,
                2,
                "handler_pc: " + et.handler_pc.value
        )));
        final int catch_type = et.catch_type.value;
        String catch_type_desc = (catch_type == 0) ? "" : " - " + classFile.getCPDescription(catch_type);
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                startPosMoving + 6,
                2,
                "catch_type: " + catch_type + catch_type_desc
        )));
    }

    /**
     * The {@code exception_table} structure in {@code Code} attribute.
     *
     * @author Amos Shi
     */
    public final static class ExceptionTable extends FileComponent {

        public static final int LENGTH = 8;
        public transient final u2 start_pc;
        public transient final u2 end_pc;
        public transient final u2 handler_pc;

        /**
         * If the value of the catch_type item is nonzero, it must be a valid
         * index into the constant_pool table. The constant_pool entry at that
         * index must be a CONSTANT_Class_info structure representing a class of
         * exceptions that this exception handler is designated to catch. The
         * exception handler will be called only if the thrown exception is an
         * instance of the given class or one of its subclasses.
         *
         * If the value of the catch_type item is zero, this exception handler
         * is called for all exceptions.
         */
        public transient final u2 catch_type;

        private ExceptionTable(final PosDataInputStream posDataInputStream) throws IOException {
            this.startPos = posDataInputStream.getPos();
            this.length = LENGTH;

            this.start_pc = new u2(posDataInputStream);
            this.end_pc = new u2(posDataInputStream);
            this.handler_pc = new u2(posDataInputStream);
            this.catch_type = new u2(posDataInputStream);
        }
    }
}
