package hw05.aop.argparser;

class ArgType {
    private final int loadOpcode;
    private final short slotSize;
    private final String typeDesc;

    public ArgType(int loadOpcode, int slotSize, String typeDesc) {
        this.loadOpcode = loadOpcode;
        this.slotSize = (short) slotSize;
        this.typeDesc = typeDesc;
    }

    public int getLoadOpcode() {
        return loadOpcode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public short getSlotSize() {
        return slotSize;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) loadOpcode;
        result = 31 * result + (int) slotSize;
        result = 31 * result + (typeDesc != null ? typeDesc.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ArgType object = (ArgType) o;

        if (loadOpcode != object.loadOpcode)
            return false;
        if (slotSize != object.slotSize)
            return false;
        return !(typeDesc != null ? !typeDesc.equals(object.typeDesc) : object.typeDesc != null);
    }
}
