(define (problem move-to-room)
	(:domain ship)
   (:objects
	shepard - captain
	dillon - navigator
	taft  - chief
	spyro - engineer 
	kryten - robot

	bridge - bridge
	transporter - transporter
	lab - lab
	lab2 - lab
	lab3 - lab
	engineering - engineering
	shuttlebay - shuttlebay

	deck1 - deck
	deck2 - deck
	deck3 - deck
	
	ls1 - liftshaft

	red-dwarf - shuttle
	companion-cube - plasma
	wheatley - heavyobj

	earth - planet
	thessia - planet
	
	cryptosporidium - radiation-belt
   )
   
   (:init
   
	(in shepard bridge)
	(in dillon bridge)
	(in kryten transporter)
;	(in spyro engineering)
	(in taft transporter)

	(in red-dwarf shuttlebay)
;	(in companion-cube earth)
	(in wheatley thessia)

	(in thessia cryptosporidium)

	(shiploc earth)
;	(not (shields_raised))

	(door bridge deck1)
;	(door lab deck1)
;	(door engineering deck2)
	(door transporter deck1)
	(door shuttlebay deck1)
;	(door lab2 deck1)
;	(door lab3 lab2)
;
;;	(lift deck1 ls1)
;	(lift deck2 ls1)
;	(lift deck3 ls1)
   )
   
   (:goal (and
;	(shields_raised)
	(in wheatley bridge)
;	(in shepard thessia)
	(not (crew_dead))
;	(shiploc thessia)
	)
   )
)
