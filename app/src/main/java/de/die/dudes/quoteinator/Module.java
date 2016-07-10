package de.die.dudes.quoteinator;

/**
 * Created by Phil on 17.04.2016.
 */
public class Module {

    private int module_id;
    private String name;
    private int docent_id;

    public Module() {
    }

    public Module(String name, int module_id, int docent_id) {
        setModule_id(module_id);
        setDocent_id(docent_id);
        setName(name);
    }

    public Module(String name) {
        setName(name);
    }

    private void setDocent_id(int id) {
        this.docent_id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getDocent_id() {
        return docent_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        return !(name != null ? !name.equals(module.name) : module.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getModule_id() {
        return module_id;
    }

    private void setModule_id(int module_id) {
        this.module_id = module_id;
    }
}
