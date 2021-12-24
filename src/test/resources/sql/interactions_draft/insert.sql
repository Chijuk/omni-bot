insert into interactions_drafts (id, userId, parentUniqueId, direction, sendMessage, text, textMessageId)
values (31, 100, 200, 'FROM_CUSTOMER', '{"message_id": 2353}', 'text 1', 300),
       (32, 101, 201, 'FROM_CUSTOMER', '{"message_id": 2354}', 'text 2', 301),
       (33, 102, 202, 'TO_CUSTOMER', '{"message_id": 2355}', 'text 3', 302),
       (34, 103, 203, 'TO_CUSTOMER', '{"message_id": 2356}', NULL, 303),
       (35, 104, 204, 'FROM_CUSTOMER', '{"message_id": 2357}', 'text 4', 304),
       (36, 104, 205, 'FROM_CUSTOMER', '{"message_id": 2357}', 'text 5', 305);