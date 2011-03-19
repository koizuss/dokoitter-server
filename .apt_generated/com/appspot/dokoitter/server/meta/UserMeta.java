package com.appspot.dokoitter.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-03-19 18:09:19")
/** */
public final class UserMeta extends org.slim3.datastore.ModelMeta<com.appspot.dokoitter.server.model.User> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.User, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.User, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.User, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.User, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.User> account = new org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.User>(this, "account", "account");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.User> spot = new org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.User>(this, "spot", "spot");

    private static final UserMeta slim3_singleton = new UserMeta();

    /**
     * @return the singleton
     */
    public static UserMeta get() {
       return slim3_singleton;
    }

    /** */
    public UserMeta() {
        super("User", com.appspot.dokoitter.server.model.User.class);
    }

    @Override
    public com.appspot.dokoitter.server.model.User entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.dokoitter.server.model.User model = new com.appspot.dokoitter.server.model.User();
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        model.setAccount((java.lang.String) entity.getProperty("account"));
        model.setSpot((java.lang.String) entity.getProperty("spot"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("version", m.getVersion());
        entity.setProperty("account", m.getAccount());
        entity.setProperty("spot", m.getSpot());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
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
        com.appspot.dokoitter.server.model.User m = (com.appspot.dokoitter.server.model.User) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        if(m.getAccount() != null){
            writer.setNextPropertyName("account");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getAccount());
        }
        if(m.getSpot() != null){
            writer.setNextPropertyName("spot");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getSpot());
        }
        writer.endObject();
    }

    @Override
    protected com.appspot.dokoitter.server.model.User jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.appspot.dokoitter.server.model.User m = new com.appspot.dokoitter.server.model.User();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        reader = rootReader.newObjectReader("account");
        decoder = new org.slim3.datastore.json.Default();
        m.setAccount(decoder.decode(reader, m.getAccount()));
        reader = rootReader.newObjectReader("spot");
        decoder = new org.slim3.datastore.json.Default();
        m.setSpot(decoder.decode(reader, m.getSpot()));
        return m;
    }
}