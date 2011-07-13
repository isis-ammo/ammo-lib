/**
 *
 */
package edu.vu.isis.ammo.core.provider;

/**
 * Miscellaneous things required by the Ammo content provider schemas.
 *
 * @author feisele
 */
public interface IAmmoSchema {

    // for use by ContentResolver.openFile() and company.
    public static final String FILE_MODE_READ_ONLY = "r";
    public static final String FILE_MODE_WRITE_ONLY = "w";
    public static final String FILE_MODE_WRITE_APPEND = "wa";
    public static final String FILE_MODE_READ_WRITE = "rw";
    public static final String FILE_MODE_READ_WRITE_TRUNC = "rwt";

}
