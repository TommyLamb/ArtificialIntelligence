(define (problem move-to-room)
	(:domain ship)
   (:objects
	shepard - captain
	dillon - navigator
	kryten - chief
	spyro - engineer 

	bridge - bridge
	transporter - transporter
	lab - lab	

	companion-cube - plasma

	earth - planet
   )
   
   (:init
   
	(in shepard bridge)
	(in dillon bridge)
	(in kryten transporter)
	(in spyro lab)
	(in companion-cube earth)
	(shiploc earth)
	(door bridge transporter)
	(door transporter bridge)
	(door bridge lab)
	(door lab bridge)
   )
   
   (:goal (and
	(in companion-cube lab)
	(not (transporter_broken))
	)
   )
)
