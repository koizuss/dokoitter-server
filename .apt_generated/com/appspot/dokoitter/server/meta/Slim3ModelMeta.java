package com.appspot.dokoitter.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-03-19 18:09:19")
/** */
public final class Slim3ModelMeta extends org.slim3.datastore.ModelMeta<com.appspot.dokoitter.server.model.Slim3Model> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Slim3Model, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Slim3Model, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.Slim3Model> prop1 = new org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.Slim3Model>(this, "prop1", "prop1");

    private static final Slim3ModelMeta slim3_singleton = new Slim3ModelMeta();

    /**
     * @return the singleton
     */
    public static Slim3ModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public Slim3ModelMeta() {
        super("Slim3Model", com.appspot.dokoitter.server.model.Slim3Model.class);
    }

    @Override
    public com.appspot.dokoitter.server.model.Slim3Model entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.dokoitter.server.model.Slim3Model model = new com.appspot.dokoitter.server.model.Slim3Model();
        model.setKey(entity.getKey());
        model.setProp1((java.lang.String) entity.getProperty("prop1"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.dokoitter.server.model.Slim3Model m = (com.appspot.dokoitter.server.model.Slim3Model) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("prop1", m.getProp1());
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.dokoitter.server.model.Slim3Model m = (com.appspot.dokoitter.server.model.Slim3Model) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.dokoitter.server.model.Slim3Model m = (com.appspot.dokoitter.server.model.Slim3Model) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        throw new IllegalStateException("The version property of the model(com.appspot.dokoitter.server.model.Slim3Model) is not defined.");
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        com.appspot.dokoitter.server.model.Slim3Model m = (com.appspot.dokoitter.server.model.Slim3Model) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getProp1() != null){
            writer.setNextPropertyName("prop1");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getProp1());
        }
        writer.endObject();
    }

    @Override
    protected com.appspot.dokoitter.server.model.Slim3Model jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.appspot.dokoitter.server.model.Slim3Model m = new com.appspot.dokoitter.server.model.Slim3Model();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("prop1");
        decoder = new org.slim3.datastore.json.Default();
        m.setProp1(decoder.decode(reader, m.getProp1()));
        return m;
    }
}