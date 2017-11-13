(define (problem relief)
	(:domain ship)
   (:objects

                deck1 - deck

                bridge - bridge
                transporter - transporter
                engineering - engineering
                shuttlebay - shuttlebay
		sickbay - sickbay

                shepard - captain
                kryten - robot
                lister - engineer
                spyro - navigator
                dumbledore - chief
		robotnik - doctor

                kerbal thessia helghan - planet
                the-good-place - asteroid-belt
		the-high-castle - radiation-belt

		asari-supplies helghan-supplies - medicalsupplies
		
		liara garrus miranda mordin - person
		

                red-dwarf - shuttle
        )

        (:init

                (door bridge deck1)
                (door transporter deck1)
                (door engineering deck1)
                (door shuttlebay deck1)
		(door sickbay deck1)

                (in shepard bridge)
                (in kryten shuttlebay)
                (in lister engineering)
                (in spyro bridge)
                (in dumbledore transporter)
		(in robotnik sickbay)

                (in red-dwarf shuttlebay)
		(in asari-supplies sickbay)
		(in helghan-supplies sickbay)

                (in helghan the-good-place)
		(in thessia the-high-castle)		

		(in liara thessia)
		(in garrus thessia)
		(in miranda thessia)
		(in mordin thessia)
		(injured liara)
		(injured garrus)
		(injured miranda)
		(injured mordin)
		
                (shiploc kerbal)
        )
	
	(:goal (and
		(in asari-supplies thessia)
		(in helghan-supplies helghan)
		(not (injured garrus))
		(not (injured miranda))
		(not (injured mordin))
		(not (injured liara))
		(shiploc thessia)
		
		(not(crew_dead))
		)
	)
)
