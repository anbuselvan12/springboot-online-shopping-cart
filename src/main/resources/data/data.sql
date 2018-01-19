//insert accounts
insert into accounts(firstname, lastname, password,  active, username)
values('Muhammad Dicka', 'Nirwansyah', '$2a$10$M/BnTGQlUxUcUF.yuwGUCOwP4qsJbkC0QDfOaLovbUWGGZ6keDTZC',  1, 'dickanirwansyah');

insert into accounts(firstname, lastname, password,  active, username)
values('admin', 'admin', '$2a$10$M/BnTGQlUxUcUF.yuwGUCOwP4qsJbkC0QDfOaLovbUWGGZ6keDTZC',  1, 'admin');

insert into accounts(firstname, lastname, password, username, active)
values('Muhammad Jhoni', 'Joni', 'root', 'jonidep', 1);

//insert roles
insert into roles(description, roles)
values ('This Is Admin Roles', 'ADMIN');

insert into roles(description, roles)
values ('This Is Users Roles', 'USERS');

//insert accounts_roles
insert into accounts_roles(idaccounts, idroles)
values(1, 1);
insert into accounts_roles(idaccounts, idroles)
values(1, 2);
insert into accounts_roles(idaccounts, idroles)
values(2, 2);
