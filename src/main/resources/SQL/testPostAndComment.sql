
insert into talk.textcomponent values (0, "test text lorem ipsum", 'Post', '1495741151337', 2, 2);

insert into talk.textcomponent values (1, "test talk2 lorem ipsum", 'Post', '1495741157337', 15, 2);

insert into talk.textcomponent values (7, "test comment lorem ipsum", 'Comment', '1496294981725', 9, 1);

insert into talk.posts values (0);
insert into talk.posts values (1);

insert into talk.comments values (7, 6, 0);

desc talk.comments;

select * from talk.comments;

select * from talk.textcomponent;
select * from talk.points;
delete from talk.points where textComponent_id=10;

update talk.textcomponent set votes=13 where id=6;

update talk.textcomponent set text="This is a Comment. ....." where id=4;