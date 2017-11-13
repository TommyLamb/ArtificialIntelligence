(define (problem get-the-cheese)
	(:domain ship)

	(:objects
		
		deck1 deck2 deck3 deck4 - deck
		
		;Deck 1
		bridge - bridge
		astrometrics - lab
		
		;Deck 2
		transporterAlpha - transporter
		anthropology kennels - lab
		sickbay - sickbay

		;Deck 3
		engineering - engineering
		cargobay - cargobay
		holographic-projection-suite - lab

		;Deck 4
		shuttlebay - shuttlebay
		cargocupboard - cargobay
		transporterBeta - transporter

		ls1 ls2 - liftshaft

		cheddar stilton brie - lightobj
		
		shepard - captain
		kryten - robot
		lister - engineer
	)

	(:init
		
		(door bridge deck1)
		(door astrometrics deck1)

		(door transporterAlpha deck2)
		(door sickbay deck2)
		(door anthropology deck2)
		(door kennels anthropology)

		(door engineering deck3)
		(door cargobay deck3)
		(door holographic-projection-suite deck3)
		
		(door shuttlebay deck4)
		(door cargocupboard shuttlebay)
		(door shuttlebay transporterBeta)

		(lift deck1 ls1)
		(lift deck2 ls1)
		(lift deck3 ls1)

		(lift deck3 ls2)
		(lift deck4 ls2)

		(in cheddar astrometrics)
		(in stilton kennels)
		(in brie cargocupboard)

		(in shepard bridge)
		(in kryten holographic-projection-suite)
		(in lister anthropology)
	)

	(:goal (and
		(holding lister brie)
		(holding kryten cheddar)
		(holding shepard stilton)
		)
	)	
)
