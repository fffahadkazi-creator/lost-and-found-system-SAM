# Campus Lost & Found Management System

Java Swing + MySQL + JDBC mini project, matching :
Login/Signup → Dashboard → Report Lost / Report Found / Search / My Reports,
plus an Admin claim-review screen (Approve/Reject → Resolved).

## Setup in Eclipse

1. **Install MySQL** (if not already) and open MySQL Workbench or the CLI.
2. Run `sql/schema.sql` — creates the database, 3 tables, and one seeded admin
   account (`admin@campus.edu` / `admin123`).
3. **New Eclipse Project** → File > New > Java Project → name it `LostFoundSystem`.
4. Copy the `src` folder contents into your project's `src` folder (keep the
   `db`, `model`, `dao`, `ui` package folders as-is).
5. **Add the MySQL JDBC driver:**
   - Download `mysql-connector-j-x.x.x.jar` from https://dev.mysql.com/downloads/connector/j/
   - Right-click project → Build Path → Configure Build Path → Libraries →
     Add External JARs → select the downloaded jar.
6. Open `src/db/DBConnection.java` and set your actual MySQL password on the
   `PASSWORD` field.
7. Run `Main.java`.

## How it maps 

- **Login/Signup** → `LoginForm.java`, `SignupForm.java`
- **Dashboard** (routes Student vs Admin) → `DashboardFrame.java`
- **Report Lost / Report Found** (item name, category, date, location,
  description) → `ReportItemFrame.java`
- **Search** (keyword + category/location matching against the opposite
  report type) → `SearchFrame.java`
- **My Report** → `MyReportsFrame.java`
- **Claim item → Admin/Owner verification → Approve/Reject → Resolved** →
  `ClaimDAO.fileClaim()` files the claim, `AdminReviewFrame.java` +
  `ClaimDAO.resolveClaim()` handle approve/reject.

