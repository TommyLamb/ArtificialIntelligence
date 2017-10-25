(define (domain ship)
    (:requirements :adl :derived-predicates)
    
	(:types
		shuttle planet heavyobj lightobj - obj
		humanoid medicalsupplies plasma - lightobj
		person robot - humanoid
		captain navigator engineer chief doctor scientist security - person		
		radiation-belt asteroid-belt room shuttle planet - place
		bridge engineering transporter lab cargobay shuttlebay sickbay deck - room
		liftshaft ;a completely abstract type to vastly simplify the declaration of lifts in the problem definition
	) ;planet and shuttle are given obj type for (in ?planet ?asteroidbelt) and (in ?shuttle ?shuttlebay)
	; note that without multiple inheritance robots cannot hold any role on the ship
	
    (:predicates 
        
        (door ?r1 ?r2 - room)
        (lift ?d1 - deck ?ls - liftshaft)
        (shiploc ?l - planet)
        (in ?o - obj ?p - place)
	(holding ?h - humanoid ?o - object)
        (ship_broken)
        (transporter_broken)
	(tired ?h - humanoid)
	(injured ?p - person)
	(connected ?r1 ?r2 - room)
	
	(shields_raised)
	(radiation_risk)
	(crew_dead)
    )

	(:derived (connected ?r1 ?r2 - room)
		(or 
			(door ?r1 ?r2) 
			(door ?r2 ?r1)
			(exists (?room - room) (and (connected ?r1 ?room) (connected ?r2 ?room)))
		)
	)

	(:derived (radiation_risk)
		(exists (?rad - radiation-belt ?p - planet) (and (shiploc ?p) (in ?p ?rad)))
	)

	(:derived (crew_dead)
		(and (radiation_risk) (not (shields_raised)))
	)
	;By ensuring the ship cannot be moved and the shields cannot be raised when crew_dead
	;The predicate cannot become false after being true at any point
    
    (:action move_using_door
        :parameters (?h - humanoid ?r1 ?r2 - room)
        :precondition (and 
		(in ?h ?r1) 
		(connected ?r1 ?r2)
	)
        :effect (and 
		(in ?h ?r2) 
		(not (in ?h ?r1))
	)
    )
    (:action move_using_lift
        :parameters (?h - humanoid ?d1 ?d2 - deck)
        :precondition (and 
		(in ?h ?d1) 
		(exists (?ls - liftshaft) (and (lift ?d1 ?ls) (lift ?d2 ?ls)))
	)
        :effect (and (in ?h ?d2) (not (in ?h ?d1)))
    )
    (:action move_ship
        :parameters (?l1 ?l2 - planet)
        :precondition (and 
            (not(ship_broken))
            (not (= ?l1 ?l2)) 
            (shiploc ?l1)
            (exists (?captain - captain ?navigator - navigator ?room - bridge) (and (in ?captain ?room) (in ?navigator ?room)))
		(not (crew_dead))
	)
        :effect (and 
			(shiploc ?l2) 
			(not(shiploc ?l1))
			(forall (?belt - asteroid-belt) 
				(when (in ?l2 ?belt)
					(ship_broken)
				)
			)
		)
    ) 
    (:action teleport_to_planet
        :parameters (?lo - lightobj ?p - planet)
        :precondition (and 
		(shiploc ?p) 
		(exists (?chief - chief ?room - transporter) (and (in ?lo ?room)(in ?chief ?room))) 
		(not (transporter_broken))
		(not (exists (?ho - heavyobj) (holding ?lo ?ho)))

		(not (shields_raised))
	)
        :effect (and 
		(in ?lo ?p)  
		(not (in ?lo ?room))
    	)
    )
    (:action teleport_from_planet
        :parameters (?lo - lightobj ?p - planet)
        :precondition (and
		 (shiploc ?p) 
		(exists (?chief - chief ?transporterroom - transporter) (in ?chief ?transporterroom)) 
		(in ?lo ?p) 
		(not (transporter_broken))	
		(not (exists (?ho - heavyobj) (holding ?lo ?ho)))
		(not (shields_raised))
	)
        :effect (and
		(not(in ?lo ?p))
		(in ?lo ?transporterroom)
		(forall (?ore - plasma)
			(when (or (= ?ore ?lo) (holding ?lo ?ore))
				(transporter_broken)
			)
		) 
	)
    )
    (:action fix_ship
        :precondition (and 
		(ship_broken)
		(exists (?engi - engineer ?engineering - engineering) (in ?engi ?engineering))
	)
        :effect (not (ship_broken))
    )
    (:action fix_transporter
        :precondition (and 
		(transporter_broken)
		(exists (?engi - engineer ?chief - chief ?transporter - transporter) (and (in ?engi ?transporter) (in ?chief ?transporter)))
	)
        :effect (not (transporter_broken))
    )
    (:action board_shuttle
        :parameters (?h - humanoid ?s - shuttle)
        :precondition (and
		(exists (?place - place) (and (in ?h ?place) (in ?s ?place)))
		(not (exists (?plasma - plasma) (holding ?h ?plasma))) ;You may not board a shuttle holding plasma (ergo plasma cannot get on board as you cannot transport it aboard)
	)
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
				
			(not (exists (?object - obj) (or (holding ?h ?object) (holding ?lo ?object)))) ;Neither the humanoid nor the light object are holding anything.	
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
	(:action pickup_heavy_object
		:parameters (?r - robot ?ho - heavyobj)
		:precondition (and				
			(not (exists (?object - obj) (or (holding ?r ?object) (holding ?ho ?object)))) ;Neither the robot nor the heavy object are holding anything.	
			(exists (?place - place)
				(and
					(in ?r ?place)
					(in ?ho ?place)
				)
			)
			(not (tired ?r))
		)
		:effect (and
			(holding ?r ?ho)
			(not (in ?ho ?place)) ;conceptually it's in the robot gripper not the room
		)
	)
	
	(:action drop_heavy_object
		:parameters (?r - robot ?ho - heavyobj)
		:precondition (and
			(holding ?r ?ho)
			(exists (?p - place) (in ?r ?p))
		)
		:effect (and
			(not (holding ?r ?ho))
			(in ?ho ?p)
			(tired ?r)
		)
	)
	
	(:action recharge
		:parameters (?r - robot)
		:precondition (and
			(tired ?r)
			(exists (?lab - lab) (in ?r ?lab))
		)
		:effect
			(not (tired ?r))
	)
	(:action heal
		:parameters (?p - person)
		:precondition (and
			(injured ?p)
			(exists (?doctor - doctor ?room - sickbay) (and (in ?doctor ?room) (in ?p ?room)))
		)
		:effect
			(not (injured ?p))
	)

	(:action raise_shields
		:precondition (and
			(not (shields_raised))
			(exists (?bridge - bridge) (or
				(exists (?cap - captain) (in ?cap ?bridge))
				(exists (?sec - security) (in ?sec ?bridge))
				(exists (?nav - navigator) (in ?nav ?bridge))
				
			))
			(not (crew_dead))
		)
		:effect (shields_raised)
	)

	(:action drop_shields
		:precondition (and
			(shields_raised)
			(exists (?bridge - bridge) (or
				(exists (?cap - captain) (in ?cap ?bridge))
				(exists (?sec - security) (in ?sec ?bridge))
				(exists (?nav - navigator) (in ?nav ?bridge))
				
			))			
		)
		:effect (not (shields_raised))
	)
)
