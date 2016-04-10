package io.github.myessentials.aperf.api.grouper;

@FunctionalInterface
public interface GroupSupplier {
    /**
     * Returns what group o belongs to.
     * @param o The Object to check
     * @return The group the Object belongs to
     */
    String group(Object o);
}
