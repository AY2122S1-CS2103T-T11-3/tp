package seedu.address.model.person;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Remark {

    private final String value;

    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remark remark = (Remark) o;
        return Objects.equals(value, remark.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
