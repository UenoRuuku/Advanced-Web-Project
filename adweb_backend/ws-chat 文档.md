Client to Server:

```json
{
	at: [string], //另有 ["everybody"] 代表 at 所有人，[""] 代表无 at
  to: [string], //转发，[""] 代表发给所有人
  message: string
}
```

Server to Client: 

```json
{
  author: string, 
  at: [string],//另有 ["everybody"] 代表 at 所有人
  to: [string], //转发，[""] 代表发给所有人
  message: string
}
```



