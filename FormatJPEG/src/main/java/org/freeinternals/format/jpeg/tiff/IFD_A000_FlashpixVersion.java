/*
 * IFD_A000_FlashpixVersion.java    Oct 28, 2010, 12:09
 *
 * Copyright 2010, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.jpeg.tiff;

import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.commonlib.core.PosDataInputStream;
import org.freeinternals.commonlib.core.FileFormatException;

/**
 *
 * @author Amos Shi
 * @see IFD_9000_ExifVersion
 * @see IFD_8769_Exif#Category_A
 * @see IFD_8769_Exif#FormatVersion
 */
public class IFD_A000_FlashpixVersion extends IFD_UNDEFINED {

    public static final int COUNT = 4;

    public IFD_A000_FlashpixVersion(final PosDataInputStream pDIS, int byteOrder, int tag, int startPosTiff, byte[] byteArrayTiff)
            throws IOException, FileFormatException {
        super(pDIS, byteOrder, tag, startPosTiff, byteArrayTiff);
        super.checkIFDCount(COUNT);
    }

    @Override
    public boolean isValue() {
        return true;
    }

    public String getFlashpixVersion() {
        return IFD_8769_Exif.getVersion(super.value);
    }

    @Override
    public void generateTreeNode(DefaultMutableTreeNode parentNode) {
        this.generateTreeNode_UNDEFINED(
                parentNode,
                String.format("%s: %s", this.getTagName(), this.getFlashpixVersion()),
                IFDMessage.getString(IFDMessage.KEY_IFD_A000_Description)
                + IFDMessage.getString(IFDMessage.KEY_IFD_8769_Exif_Category_A)
                + IFDMessage.getString(IFDMessage.KEY_IFD_8769_Exif_FormatVersion));
    }
}
