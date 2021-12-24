package ua.omniway.client.ot.soap.types;

import java.util.Objects;

public class OtFolder {
    private final String alias;
    private final OtFolder parentFolder;

    public OtFolder(String alias, OtFolder parentFolder) {
        Objects.requireNonNull(alias, "Folder alias can not be null");
        if (alias.isEmpty()) {
            throw new IllegalArgumentException("Folder alias can not be empty");
        }
        this.alias = alias;
        this.parentFolder = parentFolder;
    }

    public String getAlias() {
        return alias;
    }

    public OtFolder getParentFolder() {
        return parentFolder;
    }

    public String getAbsolutPath() {
        if (parentFolder == null) {
            return getAlias();
        }
        return parentFolder.getAbsolutPath().concat("\\").concat(this.getAlias());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtFolder otFolder = (OtFolder) o;
        return getAbsolutPath().equals(otFolder.getAbsolutPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, parentFolder);
    }
}