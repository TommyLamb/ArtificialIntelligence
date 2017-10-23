(define (domain ship)
    (:requirements :adl); :domain-axioms)
    
	(:types
		shuttle planet heavyobj lightobj - obj
		humanoid medicalsupplies plasma - lightobj
		person robot - humanoid
		captain navigator engineer chief doctor scientist security - person		
		asteroid-belt room shuttle planet - place
		bridge engineering transporter lab cargobay shuttlebay sickbay - room
	) ;planet and shuttle are given obj type for (in ?planet ?asteroidbelt) and (in ?shuttle ?shuttlebay)
	; note that without multiple inheritance robots cannot hold any role on the ship
	
    (:predicates 
        
        (door ?r1 ?r2 - room)
        (elevator ?r1 ?r2 - room)
        (shiploc ?l - planet)
        (in ?o - obj ?p - place)
	(holding ?h - humanoid ?o - object)
        (ship_broken)
        (transporter_broken)
	(tired ?h - humanoid)
	(injured ?p - person)
	;(connected ?r1 ?r2 - room)
    )

;	(:axiom
;		:vars (?r1 ?r2 - room)
;		:context (or (door ?r1 ?r2) (door ?r2 ?r1))
;		:implies (connected ?r1 ?r2)
;	)
    
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
		(not (exists (?plasma - plasma) (holding ?h ?plasma))) ;You may not board a shuttle holding plasma (ergo plasma cannot get on board as you cannot transport to a place (only a room))
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
)
