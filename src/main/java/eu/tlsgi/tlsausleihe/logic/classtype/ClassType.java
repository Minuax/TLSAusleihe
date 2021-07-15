package eu.tlsgi.tlsausleihe.logic.classtype;

public class ClassType {

    private int classTypeID;
    private String classTypeName, classTypeRoom;

    public ClassType(int classTypeID, String classTypeName, String classTypeRoom){
        this.classTypeID = classTypeID;
        this.classTypeName = classTypeName;
        this.classTypeRoom = classTypeRoom;
    }

    public int getClassTypeID() { return classTypeID; }

    public String getClassTypeName() { return classTypeName; }

    public String getClassTypeRoom() { return classTypeRoom; }

    @Override
    public String toString() {
        return classTypeName;
    }
}
