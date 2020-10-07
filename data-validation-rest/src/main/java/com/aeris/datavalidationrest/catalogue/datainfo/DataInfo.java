package com.aeris.datavalidationrest.catalogue.datainfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class DataInfo {
    @Id
    private String id;
    private ResourceTitle resourceTitle;
    private ResourceAbstract resourceAbstract;
    private LastModification lastModification;
    private String aerisDataCenter;
    private TemporalExtent[] temporalExtents;
    private SpatialExtent[] spatialExtents;
    private Publication[] publications;
    private Link[] links;
    private Identifier[] identifiers;
    private String status;
    private Visibility visibility;
    private DistributionInformation distributionInformation;
    private String language;
    private Contact[] contacts;
    private Quicklook[] quicklooks;
    private Keyword[] keywords;
    private Modification[] modifications;
    private Genealogy genealogy;
    private Format[] formats;
    private String dataLevel;
    private String programName;
    private String collectionName;
    private String clientTemplateName;
    private Parameter[] parameters;
    private InstrumentName[] instruments;
    private Plateform[] plateforms;
    private Project[] project;
    private DatasetSpecification datasetSpecification;
    private int documentRating;
    private int note;
    private String metadataLevel;
    private String identifier;
    private String type;

    public DataInfo() {}

    public DataInfo(ResourceTitle resourceTitle, ResourceAbstract resourceAbstract, LastModification lastModification,
                    String aerisDataCenter, TemporalExtent[] temporalExtents, SpatialExtent[] spatialExtents,
                    Publication[] publications, Link[] links, Identifier[] identifiers, String status, Visibility visibility,
                    DistributionInformation distributionInformation, String language, Contact[] contacts, Quicklook[] quicklooks,
                    Keyword[] keywords, Modification[] modifications, Genealogy genealogy, Format[] formats, String dataLevel,
                    String programName, String collectionName, String clientTemplateName, Parameter[] parameters, InstrumentName[] instruments,
                    Plateform[] plateforms, Project[] project, DatasetSpecification datasetSpecification,
                    int documentRating, int note, String metadataLevel, String identifier, String type) {
        this.resourceTitle = resourceTitle;
        this.resourceAbstract = resourceAbstract;
        this.lastModification = lastModification;
        this.aerisDataCenter = aerisDataCenter;
        this.temporalExtents = temporalExtents;
        this.spatialExtents = spatialExtents;
        this.publications = publications;
        this.links = links;
        this.identifiers = identifiers;
        this.status = status;
        this.visibility = visibility;
        this.distributionInformation = distributionInformation;
        this.language = language;
        this.contacts = contacts;
        this.quicklooks = quicklooks;
        this.keywords = keywords;
        this.modifications = modifications;
        this.genealogy = genealogy;
        this.formats = formats;
        this.dataLevel = dataLevel;
        this.programName = programName;
        this.collectionName = collectionName;
        this.clientTemplateName = clientTemplateName;
        this.parameters = parameters;
        this.instruments = instruments;
        this.plateforms = plateforms;
        this.project = project;
        this.datasetSpecification = datasetSpecification;
        this.documentRating = documentRating;
        this.note = note;
        this.metadataLevel = metadataLevel;
        this.identifier = identifier;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResourceTitle getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(ResourceTitle resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public ResourceAbstract getResourceAbstract() {
        return resourceAbstract;
    }

    public void setResourceAbstract(ResourceAbstract resourceAbstract) {
        this.resourceAbstract = resourceAbstract;
    }

    public LastModification getLastModification() {
        return lastModification;
    }

    public void setLastModification(LastModification lastModification) {
        this.lastModification = lastModification;
    }

    public String getAerisDataCenter() {
        return aerisDataCenter;
    }

    public void setAerisDataCenter(String aerisDataCenter) {
        this.aerisDataCenter = aerisDataCenter;
    }

    public TemporalExtent[] getTemporalExtents() {
        return temporalExtents;
    }

    public void setTemporalExtents(TemporalExtent[] temporalExtents) {
        this.temporalExtents = temporalExtents;
    }

    public SpatialExtent[] getSpatialExtents() {
        return spatialExtents;
    }

    public void setSpatialExtents(SpatialExtent[] spatialExtents) {
        this.spatialExtents = spatialExtents;
    }

    public Publication[] getPublications() {
        return publications;
    }

    public void setPublications(Publication[] publications) {
        this.publications = publications;
    }

    public Link[] getLinks() {
        return links;
    }

    public void setLinks(Link[] links) {
        this.links = links;
    }

    public Identifier[] getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Identifier[] identifiers) {
        this.identifiers = identifiers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public DistributionInformation getDistributionInformation() {
        return distributionInformation;
    }

    public void setDistributionInformation(DistributionInformation distributionInformation) {
        this.distributionInformation = distributionInformation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }

    public Quicklook[] getQuicklooks() {
        return quicklooks;
    }

    public void setQuicklooks(Quicklook[] quicklooks) {
        this.quicklooks = quicklooks;
    }

    public Keyword[] getKeywords() {
        return keywords;
    }

    public void setKeywords(Keyword[] keywords) {
        this.keywords = keywords;
    }

    public Modification[] getModifications() {
        return modifications;
    }

    public void setModifications(Modification[] modifications) {
        this.modifications = modifications;
    }

    public Genealogy getGenealogy() {
        return genealogy;
    }

    public void setGenealogy(Genealogy genealogy) {
        this.genealogy = genealogy;
    }

    public Format[] getFormats() {
        return formats;
    }

    public void setFormats(Format[] formats) {
        this.formats = formats;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getClientTemplateName() {
        return clientTemplateName;
    }

    public void setClientTemplateName(String clientTemplateName) {
        this.clientTemplateName = clientTemplateName;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public InstrumentName[] getInstruments() {
        return instruments;
    }

    public void setInstruments(InstrumentName[] instruments) {
        this.instruments = instruments;
    }

    public Plateform[] getPlateforms() {
        return plateforms;
    }

    public void setPlateforms(Plateform[] plateforms) {
        this.plateforms = plateforms;
    }

    public Project[] getProject() {
        return project;
    }

    public void setProject(Project[] project) {
        this.project = project;
    }

    public DatasetSpecification getDatasetSpecification() {
        return datasetSpecification;
    }

    public void setDatasetSpecification(DatasetSpecification datasetSpecification) {
        this.datasetSpecification = datasetSpecification;
    }

    public int getDocumentRating() {
        return documentRating;
    }

    public void setDocumentRating(int documentRating) {
        this.documentRating = documentRating;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getMetadataLevel() {
        return metadataLevel;
    }

    public void setMetadataLevel(String metadataLevel) {
        this.metadataLevel = metadataLevel;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "id='" + id + '\'' +
                ", resourceTitle=" + resourceTitle +
                ", resourceAbstract=" + resourceAbstract +
                ", lastModification=" + lastModification +
                ", aerisDataCenter='" + aerisDataCenter + '\'' +
                ", temporalExtents=" + Arrays.toString(temporalExtents) +
                ", spatialExtents=" + Arrays.toString(spatialExtents) +
                ", publications=" + Arrays.toString(publications) +
                ", links=" + Arrays.toString(links) +
                ", identifiers=" + Arrays.toString(identifiers) +
                ", status='" + status + '\'' +
                ", visibility=" + visibility +
                ", distributionInformation=" + distributionInformation +
                ", language='" + language + '\'' +
                ", contacts=" + Arrays.toString(contacts) +
                ", quicklooks=" + Arrays.toString(quicklooks) +
                ", keywords=" + Arrays.toString(keywords) +
                ", modifications=" + Arrays.toString(modifications) +
                ", genealogy=" + genealogy +
                ", formats=" + Arrays.toString(formats) +
                ", dataLevel='" + dataLevel + '\'' +
                ", programName='" + programName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", clientTemplateName='" + clientTemplateName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", instruments=" + Arrays.toString(instruments) +
                ", plateforms=" + Arrays.toString(plateforms) +
                ", project=" + Arrays.toString(project) +
                ", datasetSpecification=" + datasetSpecification +
                ", documentRating=" + documentRating +
                ", note=" + note +
                ", metadataLevel='" + metadataLevel + '\'' +
                ", identifier='" + identifier + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ResourceTitle {
        private String en;
        private String fr;

        public ResourceTitle() {}

        public ResourceTitle(String en, String fr) {
            this.en = en;
            this.fr = fr;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        @Override
        public String toString() {
            return "ResourceTitle{" +
                    "en='" + en + '\'' +
                    ", fr='" + fr + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ResourceAbstract {
        private String en;
        private String fr;

        public ResourceAbstract() {}

        public ResourceAbstract(String en, String fr) {
            this.en = en;
            this.fr = fr;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        @Override
        public String toString() {
            return "ResourceAbstract{" +
                    "en='" + en + '\'' +
                    ", fr='" + fr + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class LastModification {
        private String value;

        public LastModification() {}

        public LastModification(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "LastModification{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TemporalExtent {
        private String beginDate;
        private String endDate;
        private String comment;

        public TemporalExtent() {}

        public TemporalExtent(String beginDate, String endDate, String comment) {
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.comment = comment;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "TemporalExtent{" +
                    "beginDate='" + beginDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", comment='" + comment + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class SpatialExtent {
        private Area area;
        private String name;
        private String description;
        private String additionalData;
        private String comment;
        private String projection;
        private String spatialRepresentation;
        private String orbit;

        public SpatialExtent() {}

        public SpatialExtent(Area area, String name, String description, String additionalData, String comment, String projection,
                             String spatialRepresentation, String orbit) {
            this.area = area;
            this.name = name;
            this.description = description;
            this.additionalData = additionalData;
            this.comment = comment;
            this.projection = projection;
            this.spatialRepresentation = spatialRepresentation;
            this.orbit = orbit;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(String additionalData) {
            this.additionalData = additionalData;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getProjection() {
            return projection;
        }

        public void setProjection(String projection) {
            this.projection = projection;
        }

        public String getSpatialRepresentation() {
            return spatialRepresentation;
        }

        public void setSpatialRepresentation(String spatialRepresentation) {
            this.spatialRepresentation = spatialRepresentation;
        }

        public String getOrbit() {
            return orbit;
        }

        public void setOrbit(String orbit) {
            this.orbit = orbit;
        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Area {
            private String type;
            private String latitude;
            private String longitude;
            private String altitude;

            public Area(){}

            public Area(String type, String latitude, String longitude, String altitude) {
                this.type = type;
                this.latitude = latitude;
                this.longitude = longitude;
                this.altitude = altitude;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getAltitude() {
                return altitude;
            }

            public void setAltitude(String altitude) {
                this.altitude = altitude;
            }

            @Override
            public String toString() {
                return "Area{" +
                        "type='" + type + '\'' +
                        ", latitude='" + latitude + '\'' +
                        ", longitude='" + longitude + '\'' +
                        ", altitude='" + altitude + '\'' +
                        '}';
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Publication {
        public Publication() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Link {
        private String type;
        private String url;
        private String name;
        private Description description;

        public Link() {}

        public Link(String type, String url, String name, Description description) {
            this.type = type;
            this.url = url;
            this.name = name;
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Link{" +
                    "type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", description=" + description +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Description {
        private String DEFAULT_VALUE_KEY;

        public Description() {}

        public Description(String DEFAULT_VALUE_KEY) {
            this.DEFAULT_VALUE_KEY = DEFAULT_VALUE_KEY;
        }

        public String getDEFAULT_VALUE_KEY() {
            return DEFAULT_VALUE_KEY;
        }

        public void setDEFAULT_VALUE_KEY(String DEFAULT_VALUE_KEY) {
            this.DEFAULT_VALUE_KEY = DEFAULT_VALUE_KEY;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Identifier {
        public Identifier() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Visibility {
        public Visibility() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class DistributionInformation {
        private String dataPolicyName;
        private String dataPolicyVersion;
        private String dataPolicyUrl;
        private String embargoStartDate;
        private int embargoDuration;
        private EmbargoRole[] embargoRoles;
        private Role[] roles;
        private boolean registrationNeeded;
        private Description description;
        private String licenceName;
        private String licenceVersion;
        private String licencePolicyUrl;
        private AccessConstraint[] accessConstraints;
        private UseConstraint[] useConstraints;

        public DistributionInformation() {}

        public DistributionInformation(String dataPolicyName, String dataPolicyVersion, String dataPolicyUrl,
                                       String embargoStartDate, int embargoDuration, EmbargoRole[] embargoRoles, Role[] roles,
                                       boolean registrationNeeded, Description description, String licenceName, String licenceVersion,
                                       String licencePolicyUrl, AccessConstraint[] accessConstraints, UseConstraint[] useConstraints) {

            this.dataPolicyName = dataPolicyName;
            this.dataPolicyVersion = dataPolicyVersion;
            this.dataPolicyUrl = dataPolicyUrl;
            this.embargoStartDate = embargoStartDate;
            this.embargoDuration = embargoDuration;
            this.embargoRoles = embargoRoles;
            this.roles = roles;
            this.registrationNeeded = registrationNeeded;
            this.description = description;
            this.licenceName = licenceName;
            this.licenceVersion = licenceVersion;
            this.licencePolicyUrl = licencePolicyUrl;
            this.accessConstraints = accessConstraints;
            this.useConstraints = useConstraints;
        }

        public String getDataPolicyName() {
            return dataPolicyName;
        }

        public void setDataPolicyName(String dataPolicyName) {
            this.dataPolicyName = dataPolicyName;
        }

        public String getDataPolicyVersion() {
            return dataPolicyVersion;
        }

        public void setDataPolicyVersion(String dataPolicyVersion) {
            this.dataPolicyVersion = dataPolicyVersion;
        }

        public String getDataPolicyUrl() {
            return dataPolicyUrl;
        }

        public void setDataPolicyUrl(String dataPolicyUrl) {
            this.dataPolicyUrl = dataPolicyUrl;
        }

        public String getEmbargoStartDate() {
            return embargoStartDate;
        }

        public void setEmbargoStartDate(String embargoStartDate) {
            this.embargoStartDate = embargoStartDate;
        }

        public int getEmbargoDuration() {
            return embargoDuration;
        }

        public void setEmbargoDuration(int embargoDuration) {
            this.embargoDuration = embargoDuration;
        }

        public EmbargoRole[] getEmbargoRoles() {
            return embargoRoles;
        }

        public void setEmbargoRoles(EmbargoRole[] embargoRoles) {
            this.embargoRoles = embargoRoles;
        }

        public Role[] getRoles() {
            return roles;
        }

        public void setRoles(Role[] roles) {
            this.roles = roles;
        }

        public boolean isRegistrationNeeded() {
            return registrationNeeded;
        }

        public void setRegistrationNeeded(boolean registrationNeeded) {
            this.registrationNeeded = registrationNeeded;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getLicenceName() {
            return licenceName;
        }

        public void setLicenceName(String licenceName) {
            this.licenceName = licenceName;
        }

        public String getLicenceVersion() {
            return licenceVersion;
        }

        public void setLicenceVersion(String licenceVersion) {
            this.licenceVersion = licenceVersion;
        }

        public String getLicencePolicyUrl() {
            return licencePolicyUrl;
        }

        public void setLicencePolicyUrl(String licencePolicyUrl) {
            this.licencePolicyUrl = licencePolicyUrl;
        }

        public AccessConstraint[] getAccessConstraints() {
            return accessConstraints;
        }

        public void setAccessConstraints(AccessConstraint[] accessConstraints) {
            this.accessConstraints = accessConstraints;
        }

        public UseConstraint[] getUseConstraints() {
            return useConstraints;
        }

        public void setUseConstraints(UseConstraint[] useConstraints) {
            this.useConstraints = useConstraints;
        }

        @Override
        public String toString() {
            return "DistributionInformation{" +
                    "dataPolicyName='" + dataPolicyName + '\'' +
                    ", dataPolicyVersion='" + dataPolicyVersion + '\'' +
                    ", dataPolicyUrl='" + dataPolicyUrl + '\'' +
                    ", embargoStartDate='" + embargoStartDate + '\'' +
                    ", embargoDuration=" + embargoDuration +
                    ", embargoRoles=" + Arrays.toString(embargoRoles) +
                    ", roles=" + Arrays.toString(roles) +
                    ", registrationNeeded=" + registrationNeeded +
                    ", description=" + description +
                    ", licenceName='" + licenceName + '\'' +
                    ", licenceVersion='" + licenceVersion + '\'' +
                    ", licencePolicyUrl='" + licencePolicyUrl + '\'' +
                    ", accessConstraints=" + Arrays.toString(accessConstraints) +
                    ", useConstraints=" + Arrays.toString(useConstraints) +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class EmbargoRole {

        public EmbargoRole() {}

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Role {
        private String pointofcontact;

        public Role() {}

        public Role(String pointofcontact) {
            this.pointofcontact = pointofcontact;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "pointofcontact='" + pointofcontact + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AccessConstraint {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UseConstraint {
        public UseConstraint() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Contact {
        private String name;
        private String email;
        private String organisation;
        private Comment comment;
        private String adress;
        private Role[] roles;
        private String orcId;

        public Contact(){}

        public Contact(String name, String email, String organisation, Comment comment, String adress, Role[] roles, String orcId) {
            this.name = name;
            this.email = email;
            this.organisation = organisation;
            this.comment = comment;
            this.adress = adress;
            this.roles = roles;
            this.orcId = orcId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOrganisation() {
            return organisation;
        }

        public void setOrganisation(String organisation) {
            this.organisation = organisation;
        }

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public Role[] getRoles() {
            return roles;
        }

        public void setRoles(Role[] roles) {
            this.roles = roles;
        }

        public String getOrcId() {
            return orcId;
        }

        public void setOrcId(String orcId) {
            this.orcId = orcId;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", organisation='" + organisation + '\'' +
                    ", comment=" + comment +
                    ", adress='" + adress + '\'' +
                    ", roles=" + Arrays.toString(roles) +
                    ", orcId='" + orcId + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Comment {
        private String DEFAULT_VALUE_KEY;
        private String en;
        private String fr;

        public Comment() {}

        public Comment(String DEFAULT_VALUE_KEY, String en, String fr) {
            this.DEFAULT_VALUE_KEY = DEFAULT_VALUE_KEY;
            this.en = en;
            this.fr = fr;
        }

        public String getDEFAULT_VALUE_KEY() {
            return DEFAULT_VALUE_KEY;
        }

        public void setDEFAULT_VALUE_KEY(String DEFAULT_VALUE_KEY) {
            this.DEFAULT_VALUE_KEY = DEFAULT_VALUE_KEY;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "DEFAULT_VALUE_KEY='" + DEFAULT_VALUE_KEY + '\'' +
                    ", en='" + en + '\'' +
                    ", fr='" + fr + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Quicklook {
        public Quicklook() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Keyword {
        public Keyword() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Modification {
        public Modification() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Genealogy {
        public Genealogy() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Format {
        private String name;
        private String version;
        private Description description;
        private ReadingInformation readingInformation;
        private TemporalInterval temporalInterval;

        public Format() {}

        public Format(String name, String version, Description description, ReadingInformation readingInformation,
                      TemporalInterval temporalInterval) {
            this.name = name;
            this.version = version;
            this.description = description;
            this.readingInformation = readingInformation;
            this.temporalInterval = temporalInterval;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public ReadingInformation getReadingInformation() {
            return readingInformation;
        }

        public void setReadingInformation(ReadingInformation readingInformation) {
            this.readingInformation = readingInformation;
        }

        public TemporalInterval getTemporalInterval() {
            return temporalInterval;
        }

        public void setTemporalInterval(TemporalInterval temporalInterval) {
            this.temporalInterval = temporalInterval;
        }

        @Override
        public String toString() {
            return "Format{" +
                    "name='" + name + '\'' +
                    ", version='" + version + '\'' +
                    ", description=" + description +
                    ", readingInformation=" + readingInformation +
                    ", temporalInterval=" + temporalInterval +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ReadingInformation {
        public ReadingInformation() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TemporalInterval {
        public TemporalInterval() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Parameter {
        private String shortName;
        private String longName;
        private String uom;
        private Comment comment;
        private String type;
        private String cfStandardName;
        private String thesaurusConcat;
        private ThesaurusVariable thesaurusVariable;

        public Parameter() {}

        public Parameter(String shortName, String longName, String uom, Comment comment, String type, String cfStandardName,
                         String thesaurusConcat, ThesaurusVariable thesaurusVariable) {
            this.shortName = shortName;
            this.longName = longName;
            this.uom = uom;
            this.comment = comment;
            this.type = type;
            this.cfStandardName = cfStandardName;
            this.thesaurusConcat = thesaurusConcat;
            this.thesaurusVariable = thesaurusVariable;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getUom() {
            return uom;
        }

        public void setUom(String uom) {
            this.uom = uom;
        }

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCfStandardName() {
            return cfStandardName;
        }

        public void setCfStandardName(String cfStandardName) {
            this.cfStandardName = cfStandardName;
        }

        public String getThesaurusConcat() {
            return thesaurusConcat;
        }

        public void setThesaurusConcat(String thesaurusConcat) {
            this.thesaurusConcat = thesaurusConcat;
        }

        public ThesaurusVariable getThesaurusVariable() {
            return thesaurusVariable;
        }

        public void setThesaurusVariable(ThesaurusVariable thesaurusVariable) {
            this.thesaurusVariable = thesaurusVariable;
        }

        @Override
        public String toString() {
            return "Parameter{" +
                    "shortName='" + shortName + '\'' +
                    ", longName='" + longName + '\'' +
                    ", uom='" + uom + '\'' +
                    ", comment=" + comment +
                    ", type='" + type + '\'' +
                    ", cfStandardName='" + cfStandardName + '\'' +
                    ", thesaurusConcat='" + thesaurusConcat + '\'' +
                    ", thesaurusVariable=" + thesaurusVariable +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ThesaurusVariable {
        private String code;
        private Name name;
        private ThesaurusVariable thesaurusVariable;

        public ThesaurusVariable() {}

        public ThesaurusVariable(String code, Name name, ThesaurusVariable thesaurusVariable) {
            this.code = code;
            this.name = name;
            this.thesaurusVariable = thesaurusVariable;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public ThesaurusVariable getThesaurusVariable() {
            return thesaurusVariable;
        }

        public void setThesaurusVariable(ThesaurusVariable thesaurusVariable) {
            this.thesaurusVariable = thesaurusVariable;
        }

        @Override
        public String toString() {
            return "ThesaurusVariable{" +
                    "code='" + code + '\'' +
                    ", name=" + name +
                    ", thesaurusVariable=" + thesaurusVariable +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Name {
        private String en;
        private String fr;

        public Name() {
        }

        public Name(String en, String fr) {
            this.en = en;
            this.fr = fr;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "en='" + en + '\'' +
                    ", fr='" + fr + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class InstrumentName {
        private String thesaurusConcat;
        private ThesaurusClass thesaurusClass;
        private String manufacturer;
        private String model;
        private String serialNumber;
        private String calibration;
        private Resolution resolution;
        private String displayName;
        private Description description;

        public InstrumentName() {}

        public InstrumentName(String thesaurusConcat, ThesaurusClass thesaurusClass, String manufacturer, String model, String serialNumber,
                              String calibration, Resolution resolution, String displayName, Description description) {
            this.thesaurusConcat = thesaurusConcat;
            this.thesaurusClass = thesaurusClass;
            this.manufacturer = manufacturer;
            this.model = model;
            this.serialNumber = serialNumber;
            this.calibration = calibration;
            this.resolution = resolution;
            this.displayName = displayName;
            this.description = description;
        }

        public String getThesaurusConcat() {
            return thesaurusConcat;
        }

        public void setThesaurusConcat(String thesaurusConcat) {
            this.thesaurusConcat = thesaurusConcat;
        }

        public ThesaurusClass getThesaurusClass() {
            return thesaurusClass;
        }

        public void setThesaurusClass(ThesaurusClass thesaurusClass) {
            this.thesaurusClass = thesaurusClass;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getCalibration() {
            return calibration;
        }

        public void setCalibration(String calibration) {
            this.calibration = calibration;
        }

        public Resolution getResolution() {
            return resolution;
        }

        public void setResolution(Resolution resolution) {
            this.resolution = resolution;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Instrument{" +
                    "thesaurusConcat='" + thesaurusConcat + '\'' +
                    ", thesaurusClass=" + thesaurusClass +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", model='" + model + '\'' +
                    ", serialNumber='" + serialNumber + '\'' +
                    ", calibration='" + calibration + '\'' +
                    ", resolution=" + resolution +
                    ", displayName='" + displayName + '\'' +
                    ", description=" + description +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ThesaurusClass {
        private String code;
        private Name name;
        private ThesaurusCode thesaurusCode;

        public ThesaurusClass() {
        }

        public ThesaurusClass(String code, Name name, ThesaurusCode thesaurusCode) {
            this.code = code;
            this.name = name;
            this.thesaurusCode = thesaurusCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public ThesaurusCode getThesaurusCode() {
            return thesaurusCode;
        }

        public void setThesaurusCode(ThesaurusCode thesaurusCode) {
            this.thesaurusCode = thesaurusCode;
        }

        @Override
        public String toString() {
            return "ThesaurusClass{" +
                    "code='" + code + '\'' +
                    ", name=" + name +
                    ", thesaurusCode=" + thesaurusCode +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ThesaurusCode {
        private String code;
        private Name name;
        private ThesaurusName thesaurusName;

        public ThesaurusCode() {
        }

        public ThesaurusCode(String code, Name name, ThesaurusName thesaurusName) {
            this.code = code;
            this.name = name;
            this.thesaurusName = thesaurusName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public ThesaurusName getThesaurusName() {
            return thesaurusName;
        }

        public void setThesaurusName(ThesaurusName thesaurusName) {
            this.thesaurusName = thesaurusName;
        }

        @Override
        public String toString() {
            return "ThesaurusCode{" +
                    "code='" + code + '\'' +
                    ", name=" + name +
                    ", thesaurusName=" + thesaurusName +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ThesaurusName {
        private String code;
        private Name name;
        private LongName longName;

        public ThesaurusName() {
        }

        public ThesaurusName(String code, Name name, LongName longName) {
            this.code = code;
            this.name = name;
            this.longName = longName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public LongName getLongName() {
            return longName;
        }

        public void setLongName(LongName longName) {
            this.longName = longName;
        }

        @Override
        public String toString() {
            return "ThesaurusName{" +
                    "code='" + code + '\'' +
                    ", name=" + name +
                    ", longName=" + longName +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class LongName {
        private String en;
        private String fr;

        public LongName() {}

        public LongName(String en, String fr) {
            this.en = en;
            this.fr = fr;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        @Override
        public String toString() {
            return "LongName{" +
                    "en='" + en + '\'' +
                    ", fr='" + fr + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Resolution {
        public Resolution() {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Plateform {
        private String thesaurusConcat;
        private ThesaurusClass thesaurusClass;
        private String name;
        private String site;
        private Description description;

        public Plateform() {}

        public Plateform(String thesaurusConcat, ThesaurusClass thesaurusClass, String name, String site, Description description) {
            this.thesaurusConcat = thesaurusConcat;
            this.thesaurusClass = thesaurusClass;
            this.name = name;
            this.site = site;
            this.description = description;
        }

        public String getThesaurusConcat() {
            return thesaurusConcat;
        }

        public void setThesaurusConcat(String thesaurusConcat) {
            this.thesaurusConcat = thesaurusConcat;
        }

        public ThesaurusClass getThesaurusClass() {
            return thesaurusClass;
        }

        public void setThesaurusClass(ThesaurusClass thesaurusClass) {
            this.thesaurusClass = thesaurusClass;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Plateform{" +
                    "thesaurusConcat='" + thesaurusConcat + '\'' +
                    ", thesaurusClass=" + thesaurusClass +
                    ", name='" + name + '\'' +
                    ", site='" + site + '\'' +
                    ", description=" + description +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Project {
        private String thesaurusConcat;
        private ThesaurusCategory thesaurusCategory;
        private String aerisProjectUuid;

        public Project() {}

        public Project(String thesaurusConcat, ThesaurusCategory thesaurusCategory, String aerisProjectUuid) {
            this.thesaurusConcat = thesaurusConcat;
            this.thesaurusCategory = thesaurusCategory;
            this.aerisProjectUuid = aerisProjectUuid;
        }

        public String getThesaurusConcat() {
            return thesaurusConcat;
        }

        public void setThesaurusConcat(String thesaurusConcat) {
            this.thesaurusConcat = thesaurusConcat;
        }

        public ThesaurusCategory getThesaurusCategory() {
            return thesaurusCategory;
        }

        public void setThesaurusCategory(ThesaurusCategory thesaurusCategory) {
            this.thesaurusCategory = thesaurusCategory;
        }

        public String getAerisProjectUuid() {
            return aerisProjectUuid;
        }

        public void setAerisProjectUuid(String aerisProjectUuid) {
            this.aerisProjectUuid = aerisProjectUuid;
        }

        @Override
        public String toString() {
            return "Project{" +
                    "thesaurusConcat='" + thesaurusConcat + '\'' +
                    ", thesaurusCategory=" + thesaurusCategory +
                    ", aerisProjectUuid='" + aerisProjectUuid + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ThesaurusCategory {
        private String code;
        private Name name;
        private ThesaurusName thesaurusName;

        public ThesaurusCategory() {}

        public ThesaurusCategory(String code, Name name, ThesaurusName thesaurusName) {
            this.code = code;
            this.name = name;
            this.thesaurusName = thesaurusName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public ThesaurusName getThesaurusName() {
            return thesaurusName;
        }

        public void setThesaurusName(ThesaurusName thesaurusName) {
            this.thesaurusName = thesaurusName;
        }

        @Override
        public String toString() {
            return "ThesaurusCategory{" +
                    "code='" + code + '\'' +
                    ", name=" + name +
                    ", thesaurusName=" + thesaurusName +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class DatasetSpecification {
        public DatasetSpecification() {}
    }
}