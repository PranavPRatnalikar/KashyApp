package com.example.kashyapp1;

public class Doctors {
    String docname,docmedregno,doccontact,docgmail,doceducation,docspeciality,docexperience,dochosname,dochosregno;

    public Doctors(String docname, String docmedregno, String doccontact, String docgmail, String doceducation, String docspeciality, String docexperience, String dochosname, String dochosregno) {
        this.docname = docname;
        this.docmedregno = docmedregno;
        this.doccontact = doccontact;
        this.docgmail = docgmail;
        this.doceducation = doceducation;
        this.docspeciality = docspeciality;
        this.docexperience = docexperience;
        this.dochosname = dochosname;
        this.dochosregno = dochosregno;
    }

    public String getDocname() {
        return docname;
    }

    public String getDocmedregno() {
        return docmedregno;
    }

    public String getDoccontact() {
        return doccontact;
    }

    public String getDocgmail() {
        return docgmail;
    }

    public String getDoceducation() {
        return doceducation;
    }

    public String getDocspeciality() {
        return docspeciality;
    }

    public String getDocexperience() {
        return docexperience;
    }

    public String getDochosname() {
        return dochosname;
    }

    public String getDochosregno() {
        return dochosregno;
    }
}
