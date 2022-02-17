package edu.asu.diging.wic.core.conceptpower.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import edu.asu.diging.wic.core.model.IConceptType;

@XmlType(name = "type", namespace = "http://www.digitalhps.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptpowerEntryType implements IConceptType {

    @XmlAttribute(name = "type_id")
    private String typeId;
    @XmlAttribute(name = "type_uri")
    private String typeUri;
    @XmlValue
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeUri() {
        return typeUri;
    }

    public void setTypeUri(String typeUri) {
        this.typeUri = typeUri;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getId() {
        return typeId;
    }

    @Override
    public void setId(String id) {
        typeId = id;
    }

    @Override
    public String getUri() {
        return typeUri;
    }

    @Override
    public void setUri(String uri) {
        typeUri = uri;
    }

    @Override
    public String getName() {
        return typeName;
    }

    @Override
    public void setName(String name) {
        typeName = name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

}
