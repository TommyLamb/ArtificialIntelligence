(define (domain ship)
    (:requirements :adl)
    
	(:types
		veryheavyobj heavyobj lightobj - obj
		humanoid medicalsupplies plasma - lightobj
		person robot - humanoid
		captain navigator engineer chief doctor - person
		shuttle - veryheavyobj
		room shuttle planet - place
		bridge engineering transporter lab cargobay shuttlebay sickbay - room
	)

    (:predicates 
        
        (door ?r1 ?r2 - room)
        (elevator ?r1 ?r2 - room)
        (shiploc ?l - planet)
        (in ?o - obj ?p - place)
	(holding ?h - humanoid ?o - object)
        (ship_broken)
        (transporter_broken)
    )
    
    (:action move_using_door
        :parameters (?h - humanoid ?r1 ?r2 - room)
        :precondition (and (in ?h ?r1) (door ?r1 ?r2))
        :effect (and (in ?h ?r2) (not (in ?h ?r1)))
    )
    (:action move_using_elevator
        :parameters (?h - humanoid ?r1 ?r2 - room)
        :precondition (and (in ?h ?r1) (elevator ?r1 ?r2))
        :effect (and (in ?h ?r2) (not (in ?h ?r1)))
    )
    (:action move_ship
        :parameters (?l1 ?l2 - planet)
        :precondition (and 
            (not(ship_broken))
            (not (= ?l1 ?l2)) 
            (shiploc ?l1)
            (exists (?captain - captain ?navigator - navigator ?room - bridge) (and (in ?captain ?room) (in ?navigator ?room)))
	)
        :effect (and (shiploc ?l2) (not(shiploc ?l1)))
    ) 
    (:action teleport_to_planet
        :parameters (?lo - lightobj ?p - planet)
        :precondition (and 
		(shiploc ?p) 
		(exists (?chief - chief ?room - transporter) (and (in ?lo ?room)(in ?chief ?room))) 
		(not (transporter_broken))
	)
        :effect (and 
		(in ?lo ?p)  
		(forall (?room - transporter) (when (in ?lo ?room) (not (in ?lo ?room))))
    	)
    )
    (:action teleport_from_planet
        :parameters (?lo - lightobj ?p - planet ?r - room)
        :precondition (and
		 (shiploc ?p) 
		(exists (?chief - chief ?transporterroom - transporter) (in ?chief ?transporterroom)) 
		(in ?lo ?p) 
		(not (transporter_broken))
	)
        :effect (and
	(not(in ?lo ?p))
	(in ?lo ?room))
    )
    (:action fix_ship
        :precondition (and 
		(ship_broken)
		(exists (?engi - engineer ?room - engineering) (in ?engi ?engineering))
	)
        :effect (not (ship_broken))
    )
    (:action board_shuttle
        :parameters (?h - humanoid ?s - shuttle)
        :precondition (exists (?place - place) (and (in ?h ?place) (in ?s ?place)))
        :effect (and
            	(in ?h ?s)
		(forall (?place - place) 
			(when (and (in ?h ?place) (not (= ?place ?s))) 
		            (not (in ?h ?place))
			)
		)
        	)
    )
    (:action alight_from_shuttle
        :parameters (?h - humanoid ?s - shuttle)
        :precondition (in ?h ?s)
        :effect (and
            	(forall (?place - place) 
			(when (in ?s ?place) 
				(in ?h ?place)
			)
		)
            	(not (in ?h ?s))
        	)
    )
    (:action send_shuttle_to_planet
        :parameters (?s - shuttle ?p - planet)
        :precondition (and 
            (shiploc ?p)
            (exists (?room - shuttlebay ?pilot - humanoid) 
		(and 
			(in ?s ?room)
			(in ?pilot ?s)
		)
	   )
	)
        :effect (and
            (in ?s ?p)
      		(not (in ?s ?room))
	)
   )
    (:action return_shuttle_to_ship
        :parameters (?s - shuttle ?p - planet ?shuttlebay - shuttlebay)
        :precondition (and 
            (shiploc ?p)
            (in ?s ?p)
		(exists (?pilot - humanoid) (in ?pilot ?s))
	)
        :effect (and
            (in ?s ?shuttlebay)
            (not (in ?s ?p))
        )
    )
	
	(:action pickup_light_object
		:parameters (?h - humanoid ?lo - lightobj)
		:precondition (and
			(forall (?o - obj)
				(not (holding ?h ?o))
			)
			(exists (?place - place)
				(and
					(in ?h ?place)
					(in ?lo ?place)
				)
			)
		)
		:effect (and
			(holding ?h ?lo)
			(not (in ?lo ?place)) ;conceptually it's in the humanoid's hand not the room
		)
	)
	(:action drop_light_object
		:parameters (?h - humanoid ?lo - lightobj)
		:precondition (and
			(holding ?h ?lo)
			(exists (?p - place) (in ?h ?p))
		)
		:effect (and
			(not (holding ?h ?lo))
			(in ?lo ?p)
		)
	)
   ; (:action move_light_equipment
   ;     :parameters (?a ?b ?p ?e)
   ;     :precondition (and 
   ;         (location ?a)
   ;         (location ?b)
   ;         (not(= ?a ?b))
   ;         (door ?a ?b)
   ;         (person ?p)
   ;         (lequipment ?e)
   ;         (personloc ?p ?a)
   ;         (lequipmentloc ?e ?a))
   ;     :effect (and
   ;         (lequipmentloc ?e ?b)
   ;         (personloc ?p ?b)
   ;         (not(lequipmentloc ?e ?a))
   ;         (not(personloc ?e ?a))
   ;         )
   ; )
   ; (:action move_light_equipment_elevator
   ;     :parameters (?a ?b ?p ?e)
   ;     :precondition (and 
   ;         (location ?a)
   ;         (location ?b)
   ;         (not(= ?a ?b))
   ;         (elevator ?a ?b)
   ;         (person ?p)
   ;         (lequipment ?e)
   ;         (personloc ?p ?a)
   ;         (lequipmentloc ?e ?a))
   ;     :effect (and
   ;         (lequipmentloc ?e ?b)
   ;         (personloc ?p ?b)
   ;         (not(lequipmentloc ?e ?a))
   ;         (not(personloc ?e ?a))
   ;         )
   ; )
)
