<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#sec-1">1. Battleship</a></li>
<li><a href="#sec-2">2. What it is about?</a></li>
<li><a href="#sec-3">3. How to play</a>
<ul>
<li><a href="#sec-3-1">3.1. The game architecture</a></li>
<li><a href="#sec-3-2">3.2. The REST API</a>
<ul>
<li><a href="#sec-3-2-1">3.2.1. Start a new game play</a></li>
<li><a href="#sec-3-2-2">3.2.2. Fire on the enemy</a></li>
<li><a href="#sec-3-2-3">3.2.3. Show the battleship plan</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>

# Battleship

# What it is about?

This game targets all developers, no  matter the language they use.
The idea behind is to make each developer write small programs and get challenged with developers.

# How to play

To play this game you must know about:

## The game architecture

The Battleship game is based on a client-server architecture.
The server side is already developed and hosts the game logic. It is available at this url:
<http://battleship.dwlo.cloudbees.net>. So all your requests will address that server.

## The REST API

This part is at your charge. You have to code your own REST client using the following APIs:

### Start a new game play

This request is made once per game play and shared with other players as game identifier.

1.  Method   : POST

2.  URI      : /games

3.  RESPONSE :

        {
            "game-id": "32060218-5c1b-4b00-8b11-be49b1479c89"
        }

### Fire on the enemy

1.  Method   : PUT

2.  URI      : /games/<game-id>/players/<player-id>/attack

3.  RESPONSE :

        {
            "attack-status": "success|failure",
            "game-status": "running|over",
            "score": {
                "D": 1,
                "L": 1
            }
        }

### Show the battleship plan

1.  Method   : GET

2.  URI      : /games/<game-id>/battlefield

3.  RESPONSE :

        -|-|-|-|-
        -|D|-|-|-
        -|-|-|L|-
        -|-|-|-|-
        -|-|-|-|-
