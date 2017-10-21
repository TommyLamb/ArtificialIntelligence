(define (problem move-to-room)
	(:domain ship)
   (:objects
     ; captain security transport_cheif navigator engineer dillon navigator
     ; sickbay bridge cargo transporter deck1 deck2 deck3 shuttlebay lounge science engineering 
     ; mars earth vulcan andoria risa nibiru remus
     ; shuttle1 shuttle2 shuttle3 
     ; medical_supplies

	shepard - captain
	netscape dillon - navigator
	bridge - bridge
	shuttlebay1 - shuttlebay
	sickbay - sickbay
	companion-cube - lightobj
	earth thessia - planet
	red-dwarf - shuttle
   )
   
   (:init
   
	(in shepard shuttlebay1)
	(in dillon shuttlebay1)
	(in netscape bridge)
	(in companion-cube shuttlebay1)
	(in red-dwarf shuttlebay1)
	(shiploc earth)
	(door shuttlebay1 bridge)
	(door shuttlebay1 sickbay)
      ;  (person captain)
      ;  (person security)
      ;  (person transport_cheif)
      ;  (person engineer)
      ;  (person dillon)
      ;  (person navigator)
      ;  
      ;  (planet mars)
      ;  (planet earth)
      ;  (planet vulcan)
      ;  (planet andoria)
      ;  (planet risa)
      ;  (planet nibiru)
      ;  (planet remus)
      ;  
      ;  
      ;  
      ;  (location sickbay)
      ;  (location bridge)
      ;  (location cargo)
      ;  (location transporter)
      ;  (location shuttlebay)
      ;  (location lounge)
      ;  (location science)
      ;  (location engineering)
      ;  (location deck1)
      ;  (location deck2)
      ;  (location deck3)
      ;  (location shuttle1)
      ;  (location shuttle2)
      ;  (location shuttle3)
      ;  (location mars)
      ;  (location earth)
      ;  (location vulcan)
      ;  (location andoria)
      ;  (location risa)
      ;  (location nibiru)
      ;  (location remus)
      ;  
      ;  (shuttle shuttle1)
      ;  (shuttle shuttle2)
      ;  (shuttle shuttle3)
      ;  
      ;  (lequipment medical_supplies)
      ;  (lequipmentloc medical_supplies sickbay)
;
;        (door bridge deck3) (door deck3 bridge)
;        (door lounge deck3) (door deck3 lounge)
;        (door transporter deck3) (door deck3 transporter)
;        (door engineering deck3) (door deck3 engineering)
;        
;        (elevator deck3 deck2) (elevator deck2 deck3)
;        
;        (door cargo deck2) (door deck2 cargo)
;        (door science deck2) (door deck2 science)
;        (door sickbay deck2) (door deck2 sickbay)
;        
;        (elevator deck2 shuttlebay) (elevator shuttlebay deck2)
;
;        (personloc captain sickbay)
;        (personloc security bridge)
;        (personloc transport_cheif sickbay)
;        (personloc engineer shuttle1)
;        (personloc dillon earth)
;        (personloc navigator cargo)
      ;       
      ;  (shiploc risa)
      ;  
      ;  
      ;  (shuttleloc shuttle1 shuttlebay)
      ;  (shuttleloc shuttle2 shuttlebay)
      ;  (shuttleloc shuttle3 shuttlebay)

   )
   
   (:goal (and 
;	(shiploc thessia)
	(in dillon earth)
;	(in shepard bridge)
	(in companion-cube thessia)
   ; (personloc dillon lounge) 
   ; (personloc captain mars)
   ; (personloc engineer lounge)
	)
)
)
