/*
 * AttributeExtended.java    12:37 AM, August 11, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile.attribute;

import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.commonlib.core.PosDataInputStream;
import org.freeinternals.commonlib.ui.JTreeNodeFileComponent;
import org.freeinternals.commonlib.core.FileFormatException;
import org.freeinternals.format.classfile.ClassFile;
import org.freeinternals.format.classfile.JavaSEVersion;
import org.freeinternals.format.classfile.u2;

/**
 * The class for the Unrecognized attribute. Non-standard attribute, all of the
 * attributes which are not defined in the VM Spec will be represented by this
 * class.
 *
 * <pre>
 *    attribute_info {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *
 *        u1 info[attribute_length];
 *    }
 * </pre>
 *
 * The {@code info} is the raw byte array data.
 *
 *
 * @author Amos Shi
 * @since Java 1.0.2
 */
public class AttributeUnrecognized extends AttributeInfo {

    transient private byte[] rawData;

    AttributeUnrecognized(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream) throws IOException, FileFormatException {
        super(nameIndex, type, posDataInputStream, ClassFile.Version.Format_45_3, JavaSEVersion.Version_1_0_2);

        if (this.attribute_length.value > 0) {
            this.rawData = new byte[this.attribute_length.value];
            int readBytes = posDataInputStream.read(this.rawData);
            if (readBytes != this.attribute_length.value) {
                throw new IOException(String.format("Failed to read %d bytes, actual bytes red %d", this.attribute_length.value, readBytes));
            }
        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code info}, in raw data format.
     *
     * @return The value of {@code info}
     */
    public byte[] getRawData() {
        return this.rawData.clone();
    }

    @Override
    public void generateTreeNode(DefaultMutableTreeNode parentNode, ClassFile classFile) {
        if (this.attribute_length.value > 0) {
            parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                    this.getStartPos() + 6,
                    this.attribute_length.value,
                    "raw data"
            )));
        }
    }
}
