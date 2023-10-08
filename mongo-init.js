db.createUser(
    {
        user: "jobOffersUser",
        pwd: "jobOffersPassword",
        roles: [
            {
                role: "readWrite",
                db: "jobOffersDataBase"
            }
        ]
    }
)
