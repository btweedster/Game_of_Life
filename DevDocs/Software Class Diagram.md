```mermaid
classDiagram
    direction LR
    Controls --> Game
    Game --> Selection
    Controls --> Library
    Library --> Board
    Controls --> Selection
    Game --> Board
    Selection --> Coord
    Board --> Selection
	Board --> Coord

    class Controls{
        Game game
        int speed
        Board selected_shape
        Library library
        Selection current_selection
        bool is_playing
        start()
        pause()
        next()
        previous()
        save_selection_to_library()
        list_shapes()
        set_shape()
        place_shape()
        select_cells()
        reset()
    }
    class Game{
        List&ltBoard&gt board_history
        int board_position
        forward()
        backward()
        display()
        place_shape()
        shape_from_selection()
    }
    class Library{
        List&ltBoard&gt shapes
        get_shape()
        save_shape()
    }
    class Board {
        Map&ltCoord,Object&gt cells
        evolve()
        check_living()
        get_shape()
        place_diff()
    }
    class Selection{
        Coord top_left
        Coord bot_right
    }
    class Coord{
        long x
        long y
    }
```

## Controls
### Attributes
- Game game
	- Instance of the game
- int speed
	- Time between frames in milliseconds
- Board selected_shape
	- Currently selected shape
- Library library
	- Shape library for saving and loading shapes
- Selection current_selection
	- Currently selected coordinates
- bool is_playing
	- If the game is playing or not
### Methods
- start()
	- sets is_playing to true
- pause()
	- sets is_playing to false
- next()
	- Moves the game one generation forward
- previous()
	- Moves the game on generation backwards
- save_selection_to_library()
	- Saves whatever is in the current selection on the current game board as a shape to the shape library
- list_shapes()
	- Gets all of the shapes from the shape library to display and select from
- set_shape(Board shape)
	- Sets the active shape from the shape library (or a selection)
- place_shape(Coord location)
	- Places the selected_shape onto the game board
	- If no shape, treat as toggling a single cell at the given coordinates
- select_cells(Coord top_left, Coord bottom_right)
	- Sets the current_selection coordinates
- reset()
	- Resets the game to initial state

## Game
### Attributes
- List\<Board\> board_history 
-  int board_position
### Methods
 - forward() -> bool
	 - increments board_position by 1, and ensures there is a generated board in that position
	 - returns True if new board has living cells, False otherwise
 - backward()
	 - decrements board_position by 1, and ensures there is a generated board in that position
	 - If already on 0 this does nothing.
 - display()
	 - displays the current board in the UI
 - place_shape(Coord location, Board shape)
	 - saves board to temp var
	 - triggers a reset
	 - Adds temp board as 0 in board_history
	 - Adds the given shape to the current board
		 - If no shape is given do the above but toggle the cell at the given coordinates
 - shape_from_selection(Selection selection) -> Board shape
	 - Creates and returns a new shape that is a sub set of the current board within the given selection area.

## Board
### Attributes
 - Map\<Coord,Object> living_cells
### Methods
 - evolve() -> Board
	 - Generate the next evolution of this board based on the game rules and return it
 - check_living() -> bool
	 - True if there are living cells on the board, false otherwise
 - get_shape(Selection selection) -> Board
	 - Return a new board that is the intersection of the selection and the board
 - place_diff(Coord location, Board shape)
	 - If shape is not null, union of shape and Board with shape centered at the given location
		 - does not kill living cells only adds more living cells
	 - If shape is null, toggle the status of the cell at location
		 - can kill a living cell

## Library
### Attributes
- List\<Board\> shapes
### Methods
- get_shapes()
- save_shape(Board shape)

## Selection
### Attributes
 - Coord top_left
 - Coord bottom_right
### Methods
- getters and setters

## Coord
### Attributes
- long x
- long y
### Methods
- getters and setters

```mermaid
---
title: UC1 - Run game
---
sequenceDiagram
	actor User
	participant Controls
	participant Game
	User->>Controls: start()
	activate Controls
	Controls->>Controls: set is_playing = True
	opt User pauses
		User->>Controls: stop()
		Controls->>Controls: is_playing = False
	end
	loop Non-blocking infinite loop
	Controls->>Controls: Thread.sleep(speed)
	opt is_playing == True
	Controls->>Game: forward()
	activate Game
	Game->>Game: board_postion++
	opt board_position >= board_history.size() 
		Game->>Board: evolve()
		activate Board
		Board->>Game: Board next_evolution
		deactivate Board
		Game->>Game: board_history.append(next_evolution)
	end
	Game->>Board: check_living()
	activate Board
	Board->>Game: bool still_alive
	deactivate Board
	Game->>Controls: bool still_alive
	deactivate Game
	Controls->>Controls: is_playing = still_alive
	end
	Controls->>Game: display()
	deactivate Controls
	activate Game
	Game->>Game: Renders board_history[board_position]
	deactivate Game
	end
```


```mermaid
---
title: UC2 - Load Shape
---
sequenceDiagram
	actor User
	participant Controls
	participant Library
	participant Game
	User->>Controls: list_shapes()
	activate Controls
	Controls->>Library: get_shapes()
	activate Library
	Library->>Controls: List<Board> shapes
	deactivate Library
	User->>Controls: set_shape(Board shape)
	Controls->>Controls: Board selected_shape = shape
	User->>Controls: place_shape(Coords location)
	Controls->>Game: place_shape(location, selected_shape)
	activate Game
	Game->>Game: Board temp = board_history[board_position]
	Game->>Game: reset()
	Game->>Board: temp.place_diff(location, selected_shape)
	activate Board
	Board->>Game: Board updated
	deactivate Board
	Game->>Game: board_history.append(updated)
	Game->>Controls: bool True
	deactivate Game
	Controls->>Controls: selected_shape = null
	deactivate Controls
```

```mermaid
---
title: UC3 - Save Shape
---
sequenceDiagram
	actor User
	User->>Controls: select_cells(Selection selection)
	activate Controls
	Controls->>Controls: current_selection = selection
	User->>Controls: save_selection_to_library()
	Controls->>Game: shape_from_selection(current_selection)
	activate Game
	Game->>Board: get_shape(current_selection)
	activate Board
	Board->>Game: Board selected_shape
	deactivate Board
	Game->>Controls: selected_shape
	deactivate Game
	Controls->>Library: save_shape(selected_shape)
	activate Library
	Library->>Library: shapes.append(selected_shape)
	Library->>Controls: bool True
	deactivate Library
	Controls->>Controls: current_selection = null
	deactivate Controls
```
