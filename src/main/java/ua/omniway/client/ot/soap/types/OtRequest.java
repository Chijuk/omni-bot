package ua.omniway.client.ot.soap.types;

import java.util.Objects;

public class OtRequest {
    private long uniqueId;

    public OtRequest() {
    }

    public OtRequest(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public OtRequest setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtRequest otRequest = (OtRequest) o;
        return uniqueId == otRequest.uniqueId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        return "OtRequest{" +
                "uniqueId=" + uniqueId +
                '}';
    }
}
