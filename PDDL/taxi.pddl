(define (problem taxi)
	(:domain ship)
 
	(:objects
		
		deck1 - deck
		
		bridge - bridge
		transporter - transporter
		engineering - engineering
		shuttlebay - shuttlebay

		shepard - captain
		kryten - robot
		lister - engineer
		spyro - navigator
		dumbledore - chief

		kerbal thessia helghan alderaan - planet
		the-good-place - asteroid-belt

		robotnik - doctor
		companion-cube - heavyobj
		substance-x - plasma
		
		red-dwarf - shuttle
	)

	(:init
		
		(door bridge deck1)
		(door transporter deck1)
		(door engineering deck1)
		(door shuttlebay deck1)

		(in shepard bridge)
		(in kryten shuttlebay)
		(in lister engineering)
		(in spyro bridge)
		(in dumbledore transporter)

		(in red-dwarf shuttlebay)

		(in substance-x thessia)
		(in companion-cube alderaan)
		(in robotnik helghan)
		
		(in helghan the-good-place)

		(shiploc kerbal)
	)

	(:goal (and
		(not (ship_broken))
		(not (transporter_broken))
		(in robotnik kerbal)
		(in companion-cube bridge)
		(in substance-x shuttlebay)
		)
	)
)

