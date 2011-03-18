package com.appspot.dokoitter.server.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public enum Status{
    	PENDING,
    	SENDED,
    	STOPED
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private Status status = Status.PENDING;
    private ModelRef<User> userRef = new ModelRef<User>(User.class);
    private ModelRef<User> followerRef = new ModelRef<User>(User.class);
    private String lastSpot;

    /**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return the userRef
	 */
	public ModelRef<User> getUserRef() {
		return userRef;
	}

	/**
	 * @return the followerRef
	 */
	public ModelRef<User> getFollowerRef() {
		return followerRef;
	}

	/**
	 * @param lastSpot the lastSpot to set
	 */
	public void setLastSpot(String lastSpot) {
		this.lastSpot = lastSpot;
	}

	/**
	 * @return the lastSpot
	 */
	public String getLastSpot() {
		return lastSpot;
	}

	/**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Follow other = (Follow) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
