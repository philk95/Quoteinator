package de.die.dudes.quoteinator.model;

/**
 * Created by Phil on 17.04.2016.
 */
public class Module {

    private int moduleID;
    private String name;
    private Docent docent;

    public Module() {
    }

    /***
     * Just for DB Selection. Otherwise moduleID will be ignored.
     *
     * @param name
     * @param module
     * @param docent
     */
    public Module(int module, String name, Docent docent) {
        setModuleID(module);
        setDocent(docent);
        setName(name);
    }

    public Module(String name, Docent docent) {
        setDocent(docent);
        setName(name);
    }

    public String getName() {
        return this.name;
    }

    public Docent getDocent() {
        return docent;
    }

    public int getID() {
        return moduleID;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDocent(Docent id) {
        this.docent = id;
    }

    private void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((docent == null) ? 0 : docent.hashCode());
        result = prime * result + moduleID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Module other = (Module) obj;
        if (docent == null) {
            if (other.docent != null)
                return false;
        } else if (!docent.equals(other.docent))
            return false;
        if (moduleID != other.moduleID)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}