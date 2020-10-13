export class User {
    initials;
    fullName;
    email;

    constructor({ family_name, given_name, email }) {
        this.initials = (family_name.charAt(0) + given_name.charAt(0)).toUpperCase()
        this.fullName = family_name + " " + given_name;
        this.email = typeof email === "string" ? email : null;
    }
}
