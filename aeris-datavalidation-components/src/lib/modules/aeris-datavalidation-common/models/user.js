export class User {
    firstName;
    lastName;
    //email;
    //role;
    //orcid;
    //instituteId;
    //instituteName;

    constructor({ firstName, lastName/*, email, role, orcid, instituteId, instituteName*/ }) {
        this.firstName = typeof firstName === "string" ? firstName : null;
        this.lastName = typeof lastName === "string" ? lastName : null;
        //this.email = typeof email === "string" ? email : null;
        //this.role = typeof role === "string" ? role : null;
        //this.orcid = typeof orcid === "string" ? orcid : null;
        //this.instituteId = typeof instituteId === "string" ? instituteId : null;
        //this.instituteName = typeof instituteName === "string" ? instituteName : null;
    }
}
