
db.createUser(
    {
        user: "rootuser",
        pwd: "rootpass",
        roles: [
            {
                role: "readWrite",
                db: "history-db"
            }
        ]
    }
);