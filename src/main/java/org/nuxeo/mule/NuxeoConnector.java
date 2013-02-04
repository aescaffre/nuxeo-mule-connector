/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.nuxeo.mule;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.lifecycle.Stop;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.nuxeo.ecm.automation.client.AutomationClient;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.model.Blob;
import org.nuxeo.ecm.automation.client.model.Blobs;
import org.nuxeo.ecm.automation.client.model.DocRef;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PropertyMap;

/**
 * Connector that uses Nuxeo Automation java client to leverage Nuxeo Rest API
 * 
 * @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 * 
 */
@Module(name = "nuxeo", schemaVersion = "1.0-SNAPSHOT")
public class NuxeoConnector {

    /**
     * Username to connect to Nuxeo Server
     */
    @Configurable
    @Placement(group = "Authentication")
    private String username;

    /**
     * Password to connect to Nuxeo Server
     */
    @Configurable
    @Password
    @Placement(group = "Authentication")
    private String password;

    /**
     * Nuxeo Server name (IP or DNS name)
     */
    @Configurable
    @Placement(group = "Connection")
    private String serverName = "localhost";

    /**
     * Port used to connect to Nuxeo Server
     */
    @Configurable
    @Placement(group = "Connection")
    private String port = "8080";

    /**
     * Context Path for Nuxeo instance
     */
    @Configurable
    @Placement(group = "Connection")
    private String contextPath = "nuxeo";

    /**
     * get Login used to connect to Nuxeo
     * 
     * @return Login used to connect to Nuxeo
     */
    public String getUsername() {
        return username;
    }

    /**
     * get Password used to connect to Nuxeo
     * 
     * @return Password used to connect to Nuxeo
     */
    public String getPassword() {
        return password;
    }

    /**
     * get Nuxeo Server Name
     * 
     * @return Nuxeo Server Name
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * get Nuxeo Server Port
     * 
     * @return Nuxeo Server Port
     */
    public String getPort() {
        return port;
    }

    /**
     * get Nuxeo Server Context pat
     * 
     * @return Nuxeo Server Context path
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * set Username used to connect to Nuxeo Server
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * set Password used to connect to Nuxeo Server
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set Nuxeo Server name
     * 
     * @param serverName
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * set port used to connect to Nuxeo Server
     * 
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * set Context path of the target Nuxeo Server
     * 
     * @param contextPath
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    private Session session;

    protected String getServerUrl() {
        return "http://" + serverName + ":" + port + "/" + contextPath
                + "/site/automation";
    }

    protected DocumentService docService;

    /**
     * Connect to Nuxeo Server via Automation java client
     * 
     * @throws ConnectionException
     */
    // @Connect
    @Start
    public void connect() throws ConnectionException {
        AutomationClient client = new HttpAutomationClient(getServerUrl());
        session = client.getSession(this.username, this.password);
        docService = session.getAdapter(DocumentService.class);
    }

    /**
     * Disconnect
     */
    // @Disconnect
    @Stop
    public void disconnect() {
        if (session != null) {
            session.close();
        }
    }

    /**
     * Are we connected
     */
    // @ValidateConnection
    public boolean isConnected() {
        return (session != null);
    }

    /**
     * Are we connected
     */
    // @ConnectionIdentifier
    public String connectionId() {
        return getServerUrl() + username;
    }

    /**
     * Get a Document from Nuxeo repository
     * 
     * @param ref the DocumentRef
     * @return a Document Object
     * @throws Exception in case of error
     */
    @Processor
    public Document getDocument(String ref) throws Exception {
        return docService.getDocument(ref);
    }

    /*    *//**
     * Get the root Document of Nuxeo Repository
     * 
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document getRootDocument() throws Exception { return
     * getDocument("/"); }
     *//**
     * Create a Document
     * 
     * @param parent
     * @param type
     * @param docName
     * @param properties
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document createDocument(String parent, String type,
     * String docName, PropertyMap properties) throws Exception { return
     * docService.createDocument(new DocRef(parent), type, docName, properties);
     * }
     *//**
     * Deletes a Document
     * 
     * @param ref
     * @throws Exception
     */
    /*
     * @Processor public void remove(String ref) throws Exception {
     * docService.remove(ref); }
     *//**
     * Copy a Document
     * 
     * @param src
     * @param targetParent
     * @param docName
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document copy(String src, String targetParent,
     * 
     * @Optional
     * 
     * @Default("") String docName) throws Exception { if (docName == null ||
     * docName.isEmpty()) { return docService.copy(new DocRef(src), new
     * DocRef(targetParent)); } else { return docService.copy(new DocRef(src),
     * new DocRef(targetParent), docName); } }
     *//**
     * Move a Document
     * 
     * @param src
     * @param targetParent
     * @param docName
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document move(String src, String targetParent,
     * 
     * @Optional
     * 
     * @Default("") String docName) throws Exception { if (docName == null ||
     * docName.isEmpty()) { return docService.move(new DocRef(src), new
     * DocRef(targetParent)); } else { return docService.move(new DocRef(src),
     * new DocRef(targetParent), docName); } }
     *//**
     * Retrievs children of a Document
     * 
     * @param docRef
     * @return a Documents List
     * @throws Exception
     */
    /*
     * @Processor public Documents getChildren(String docRef) throws Exception {
     * return docService.getChildren(new DocRef(docRef)); }
     *//**
     * Get a children
     * 
     * @param docRef
     * @param docName
     * @return a Document Objects
     * @throws Exception
     */
    /*
     * @Processor public Document getChild(String docRef, String docName) throws
     * Exception { return docService.getChild(new DocRef(docRef), docName); }
     *//**
     * Get Parent Document
     * 
     * @param docRef
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document getParent(String docRef) throws Exception {
     * return docService.getParent(new DocRef(docRef)); }
     *//**
     * Runs a NXQL Quetry against repository
     * 
     * @param query
     * @return a Documents List
     * @throws Exception
     */
    /*
     * @Processor public Documents query(String query) throws Exception { return
     * docService.query(query); }
     *//**
     * Set Permission
     * 
     * @param doc
     * @param user
     * @param permission
     * @param acl
     * @param granted
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document setPermission(String doc, String user, String
     * permission, String acl, boolean granted) throws Exception { return
     * docService.setPermission(new DocRef(doc), user, permission, acl,
     * granted); }
     *//**
     * Removes an ACL
     * 
     * @param doc
     * @param acl
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document removeAcl(String doc, String acl) throws
     * Exception { return docService.removeAcl(new DocRef(doc), acl); }
     *//**
     * Set Lifecycle State
     * 
     * @param doc
     * @param state
     * @return a Document Object
     * @throws Exceptions
     */
    /*
     * @Processor public Document setState(String doc, String state) throws
     * Exception { return docService.setState(new DocRef(doc), state);
     * 
     * }
     *//**
     * Locks a Document
     * 
     * @param doc
     * @param lock
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document lock(String doc, String lock) throws Exception
     * { return docService.lock(new DocRef(doc), lock); }
     *//**
     * Unlocks a Document
     * 
     * @param doc
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document unlock(String doc) throws Exception { return
     * docService.unlock(new DocRef(doc)); }
     *//**
     * Change a property on a Document
     * 
     * @param doc
     * @param key
     * @param value
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document setProperty(String doc, String key, String
     * value) throws Exception { return docService.setProperty(new DocRef(doc),
     * key, value); }
     *//**
     * Remove a Property on a Document
     * 
     * @param doc
     * @param key
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document removeProperty(String doc, String key) throws
     * Exception { return docService.removeProperty(new DocRef(doc), key); }
     *//**
     * Updates a Document
     * 
     * @param doc
     * @param properties
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document update(String doc, PropertyMap properties)
     * throws Exception { return docService.update(new DocRef(doc), properties);
     * }
     *//**
     * Publish a Document
     * 
     * @param doc
     * @param section
     * @param override
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document publish(String doc, String section, @Optional
     * 
     * @Default("false") boolean override) throws Exception { return
     * docService.publish(new DocRef(doc), new DocRef(section), override); }
     *//**
     * Create a Relation
     * 
     * @param subject
     * @param predicate
     * @param object
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document createRelation(String subject, String
     * predicate, DocRef object) throws Exception { return
     * docService.createRelation(new DocRef(subject), predicate, object); }
     *//**
     * get Relations
     * 
     * @param doc
     * @param predicate
     * @param outgoing
     * @return list of linked Document Objects
     * @throws Exception
     */
    /*
     * @Processor public Documents getRelations(String doc, String predicate,
     * boolean outgoing) throws Exception { return docService.getRelations(new
     * DocRef(doc), predicate, outgoing); }
     *//**
     * Attach a Blob to a Document
     * 
     * @param doc
     * @param blob
     * @param xpath
     * @throws Exception
     */
    /*
     * @Processor public void setBlob(String doc, Blob blob, @Optional
     * 
     * @Default("") String xpath) throws Exception {
     * 
     * if (xpath == null || xpath.isEmpty()) { docService.setBlob(new
     * DocRef(doc), blob); } else { docService.setBlob(new DocRef(doc), blob,
     * xpath); } }
     *//**
     * Remove a Blob from a Document
     * 
     * @param doc
     * @param xpath
     * @throws Exception
     */
    /*
     * @Processor public void removeBlob(String doc, @Optional
     * 
     * @Default("") String xpath) throws Exception {
     * 
     * if (xpath == null || xpath.isEmpty()) { docService.removeBlob(new
     * DocRef(doc)); } else { docService.removeBlob(new DocRef(doc), xpath); } }
     *//**
     * get the Blob associated to a Document
     * 
     * @param doc
     * @param xpath
     * @return a FileBlob object
     * @throws Exception
     */
    /*
     * @Processor public FileBlob getBlob(String doc, @Optional
     * 
     * @Default("") String xpath) throws Exception {
     * 
     * if (xpath == null || xpath.isEmpty()) { return docService.getBlob(new
     * DocRef(doc)); } else { return docService.getBlob(new DocRef(doc), xpath);
     * } }
     *//**
     * get the Blobs associated to a Document
     * 
     * @param doc
     * @param xpath
     * @return a list of Blobs
     * @throws Exception
     */
    /*
     * @Processor public Blobs getBlobs(String doc, @Optional
     * 
     * @Default("") String xpath) throws Exception {
     * 
     * if (xpath == null || xpath.isEmpty()) { return docService.getBlobs(new
     * DocRef(doc)); } else { return docService.getBlobs(new DocRef(doc),
     * xpath); } }
     *//**
     * Creates a version
     * 
     * @param doc
     * @param increment
     * @return a Document Object
     * @throws Exception
     */
    /*
     * @Processor public Document createVersion(String doc, @Optional
     * 
     * @Default("") String increment) throws Exception {
     * 
     * if (increment == null || increment.isEmpty()) { return
     * docService.createVersion(new DocRef(doc)); } else { return
     * docService.createVersion(new DocRef(doc), increment); } }
     *//**
     * Fire an Event
     * 
     * @param event
     * @param doc
     * @throws Exception
     */
    /*
     * @Processor public void fireEvent(String event, @Optional
     * 
     * @Default("") String doc) throws Exception { if (doc == null ||
     * doc.isEmpty()) { docService.fireEvent(event); } else {
     * docService.fireEvent(new DocRef(doc), event); } }
     */
}
