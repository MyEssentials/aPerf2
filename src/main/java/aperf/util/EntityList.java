package aperf.util;

import aperf.APerf;
import aperf.util.context.ContextAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.*;

// Based on https://github.com/nallar/TickProfiler/blob/master/src/main/java/nallar/tickprofiler/minecraft/entitylist/EntityList.java

/**
 * Overrides World.loadedEntityList or World.loadedTileEntityList
 *
 * @param <T>
 */
public abstract class EntityList<T> extends ArrayList<T> {
    private static final ContextAccess contextAccess = ContextAccess.$;
    protected final ArrayList<T> innerList;
    protected final World world;
    private final Field overriddenField;

    public EntityList(World world, Field overriddenField) {
        this.world = world;
        this.overriddenField = overriddenField;
        this.overriddenField.setAccessible(true);
        ArrayList<T> worldList = new ArrayList<T>();
        try {
            worldList = (ArrayList<T>) this.overriddenField.get(world);
            if (worldList.getClass() != ArrayList.class ) {
                APerf.LOG.fatal("CLag: Another mod has replaced an entity list with {}", worldList.getClass());
            }
        } catch(Throwable t) {
            APerf.LOG.fatal("Failed to get {} in world {}", overriddenField.getName(), world);
        }
        innerList = worldList;
        try {
            this.overriddenField.set(world, this);
        } catch (Exception e) {
            throw new RuntimeException("CLag: Failed to override " + overriddenField.getName() + " in world " + (world), e);
        }
    }

    /**
     * The actual tick implementation.
     */
    public abstract void tick();

    /**
     * Returns if we should override the tick or not.
     * @return If we override the tick or not
     */
    protected abstract boolean tickOverride();

    private void doTick() {
        try {
            tick();
        } catch ( Throwable t ) {
        }
    }

    public void unhook() throws IllegalAccessException {
        overriddenField.set(world, innerList); // TODO Make unhooking better?
    }

    @Override
    public int size() {
        if (tickOverride() && World.class.isAssignableFrom(contextAccess.getContext(1))) {
            Class secondCaller = contextAccess.getContext(2);
            if (secondCaller == MinecraftServer.class || World.class.isAssignableFrom(secondCaller)) {
                doTick();
                return 0;
            }
        }

        return innerList.size();
    }

    @Override
    public Iterator<T> iterator() {
        if (tickOverride() && World.class.isAssignableFrom(contextAccess.getContext(1))) {
            Class secondCaller = contextAccess.getContext(2);
            if (secondCaller == MinecraftServer.class || World.class.isAssignableFrom(secondCaller)) {
                doTick();
                return Collections.<T>emptyList().iterator();
            }
        }

        return innerList.iterator();
    }

    /** Everything below passes through to underlying ArrayList **/

    @Override
    public void trimToSize() {
        innerList.trimToSize();
    }

    @Override
    public void ensureCapacity(final int minCapacity) {
        innerList.ensureCapacity(minCapacity);
    }

    @Override
    public boolean isEmpty() {
        return innerList.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return innerList.contains(o);
    }

    @Override
    public int indexOf(final Object o) {
        return innerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return innerList.lastIndexOf(o);
    }

    @Override
    public Object clone() {
        return innerList.clone();
    }

    @Override
    public Object[] toArray() {
        return innerList.toArray();
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        return innerList.toArray(a);
    }

    @Override
    public T get(final int index) {
        return innerList.get(index);
    }

    @Override
    public T set(final int index, final T element) {
        return innerList.set(index, element);
    }

    @Override
    public boolean add(final T t) {
        return innerList.add(t);
    }

    @Override
    public void add(final int index, final T element) {
        innerList.add(index, element);
    }

    @Override
    public T remove(final int index) {
        return innerList.remove(index);
    }

    @Override
    public boolean remove(final Object o) {
        return innerList.remove(o);
    }

    @Override
    public void clear() {
        innerList.clear();
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        return innerList.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {
        return innerList.addAll(index, c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return innerList.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return innerList.retainAll(c);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return innerList.listIterator(index);
    }

    @Override
    public ListIterator<T> listIterator() {
        return innerList.listIterator();
    }

    @Override
    public List<T> subList(final int fromIndex, final int toIndex) {
        return innerList.subList(fromIndex, toIndex);
    }
}
