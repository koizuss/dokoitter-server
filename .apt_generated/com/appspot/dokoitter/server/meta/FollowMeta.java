package com.appspot.dokoitter.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-03-20 00:57:12")
/** */
public final class FollowMeta extends org.slim3.datastore.ModelMeta<com.appspot.dokoitter.server.model.Follow> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, com.appspot.dokoitter.server.model.Follow.Status> status = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, com.appspot.dokoitter.server.model.Follow.Status>(this, "status", "status", com.appspot.dokoitter.server.model.Follow.Status.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<com.appspot.dokoitter.server.model.Follow, org.slim3.datastore.ModelRef<com.appspot.dokoitter.server.model.User>, com.appspot.dokoitter.server.model.User> userRef = new org.slim3.datastore.ModelRefAttributeMeta<com.appspot.dokoitter.server.model.Follow, org.slim3.datastore.ModelRef<com.appspot.dokoitter.server.model.User>, com.appspot.dokoitter.server.model.User>(this, "userRef", "userRef", org.slim3.datastore.ModelRef.class, com.appspot.dokoitter.server.model.User.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<com.appspot.dokoitter.server.model.Follow, org.slim3.datastore.ModelRef<com.appspot.dokoitter.server.model.User>, com.appspot.dokoitter.server.model.User> followerRef = new org.slim3.datastore.ModelRefAttributeMeta<com.appspot.dokoitter.server.model.Follow, org.slim3.datastore.ModelRef<com.appspot.dokoitter.server.model.User>, com.appspot.dokoitter.server.model.User>(this, "followerRef", "followerRef", org.slim3.datastore.ModelRef.class, com.appspot.dokoitter.server.model.User.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.Follow> lastSpot = new org.slim3.datastore.StringAttributeMeta<com.appspot.dokoitter.server.model.Follow>(this, "lastSpot", "lastSpot");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.util.Date> createAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.util.Date>(this, "createAt", "createAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.appspot.dokoitter.server.model.Follow, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    private static final org.slim3.datastore.CreationDate slim3_createAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final FollowMeta slim3_singleton = new FollowMeta();

    /**
     * @return the singleton
     */
    public static FollowMeta get() {
       return slim3_singleton;
    }

    /** */
    public FollowMeta() {
        super("Follow", com.appspot.dokoitter.server.model.Follow.class);
    }

    @Override
    public com.appspot.dokoitter.server.model.Follow entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.appspot.dokoitter.server.model.Follow model = new com.appspot.dokoitter.server.model.Follow();
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        model.setStatus(stringToEnum(com.appspot.dokoitter.server.model.Follow.Status.class, (java.lang.String) entity.getProperty("status")));
        if (model.getUserRef() == null) {
            throw new NullPointerException("The property(userRef) is null.");
        }
        model.getUserRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("userRef"));
        if (model.getFollowerRef() == null) {
            throw new NullPointerException("The property(followerRef) is null.");
        }
        model.getFollowerRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("followerRef"));
        model.setLastSpot((java.lang.String) entity.getProperty("lastSpot"));
        model.setCreateAt((java.util.Date) entity.getProperty("createAt"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("version", m.getVersion());
        entity.setProperty("status", enumToString(m.getStatus()));
        if (m.getUserRef() == null) {
            throw new NullPointerException("The property(userRef) must not be null.");
        }
        entity.setProperty("userRef", m.getUserRef().getKey());
        if (m.getFollowerRef() == null) {
            throw new NullPointerException("The property(followerRef) must not be null.");
        }
        entity.setProperty("followerRef", m.getFollowerRef().getKey());
        entity.setProperty("lastSpot", m.getLastSpot());
        entity.setProperty("createAt", m.getCreateAt());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        if (m.getUserRef() == null) {
            throw new NullPointerException("The property(userRef) must not be null.");
        }
        m.getUserRef().assignKeyIfNecessary(ds);
        if (m.getFollowerRef() == null) {
            throw new NullPointerException("The property(followerRef) must not be null.");
        }
        m.getFollowerRef().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
        m.setCreateAt(slim3_createAtAttributeListener.prePut(m.getCreateAt()));
        m.setUpdatedAt(slim3_updatedAtAttributeListener.prePut(m.getUpdatedAt()));
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
        com.appspot.dokoitter.server.model.Follow m = (com.appspot.dokoitter.server.model.Follow) model;
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
        if(m.getStatus() != null){
            writer.setNextPropertyName("status");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getStatus());
        }
        if(m.getUserRef() != null && m.getUserRef().getKey() != null){
            writer.setNextPropertyName("userRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUserRef(), maxDepth, currentDepth);
        }
        if(m.getFollowerRef() != null && m.getFollowerRef().getKey() != null){
            writer.setNextPropertyName("followerRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFollowerRef(), maxDepth, currentDepth);
        }
        if(m.getLastSpot() != null){
            writer.setNextPropertyName("lastSpot");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLastSpot());
        }
        if(m.getCreateAt() != null){
            writer.setNextPropertyName("createAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreateAt());
        }
        if(m.getUpdatedAt() != null){
            writer.setNextPropertyName("updatedAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUpdatedAt());
        }
        writer.endObject();
    }

    @Override
    protected com.appspot.dokoitter.server.model.Follow jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.appspot.dokoitter.server.model.Follow m = new com.appspot.dokoitter.server.model.Follow();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        reader = rootReader.newObjectReader("status");
        decoder = new org.slim3.datastore.json.Default();
        m.setStatus(decoder.decode(reader, m.getStatus(), com.appspot.dokoitter.server.model.Follow.Status.class));
        reader = rootReader.newObjectReader("userRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getUserRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("followerRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getFollowerRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("lastSpot");
        decoder = new org.slim3.datastore.json.Default();
        m.setLastSpot(decoder.decode(reader, m.getLastSpot()));
        reader = rootReader.newObjectReader("createAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreateAt(decoder.decode(reader, m.getCreateAt()));
        reader = rootReader.newObjectReader("updatedAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setUpdatedAt(decoder.decode(reader, m.getUpdatedAt()));
        return m;
    }
}