| Controller        | Description                          | Path               | Method | Request                                          | Response                                                     |
| ----------------- | ------------------------------------ | ------------------ | ------ | ------------------------------------------------ | ------------------------------------------------------------ |
| UserController    | 获取用户 avatar ID                   | /user/avatar       | GET    | username: string                                 | id: int                                                      |
| HistoryController | 获取用户参与过的游戏记录             | /history/game      | GET    | username: string                                 | games: [{<br />create_time: string<br />end_time: string<br />step_num: int<br />}] |
| HistoryController | 获取一盘游戏中的全部棋盘历史状态记录 | /history/hanoi/all | GET    | game_id: int                                     | hanois:[{<br />first_tower: string<br />second_tower: string<br />third_tower: string<br />last_move_user_id: int<br />}] |
| HistoryController | 获取特定一步的棋盘历史状态           | /history/hanoi     | GET    | hanoi_history_id: int                            | {<br />first_tower: string<br />second_tower: string<br />third_tower: string<br />last_move_user_id: int<br />} |
| HistoryController | 获取用户在某局游戏中的操作记录       | /history/operation | GET    | {<br />username: string<br />game_id: int<br />} | before_operation_hanoi_history_id: int<br />operation_history: {<br />target_hanoi: int<br />from_tower: int<br />to_tower: int<br />} |



**game_history:**

create_time: string

end_time: string

step_num: int



**hanoi_history:**

// string: 1-4 从上到下顺序

hanoi_history_id: int

first_tower: string

second_tower: string

third_tower: string

game_id: int

operation_id

**operation_history:**

user_id: int

target_hanoi: int (1-4)

from_tower: int (1-3)

to_tower: int (1-3)