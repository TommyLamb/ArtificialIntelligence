(define (domain ship)
    (:requirements :strips)
    
    (:predicates 
        (location ?r)
        (person ?p)
        (shuttle ?s)
        (door ?r1 ?r2)
        (elevator ?r1 ?r2)
        (planet ?l)
        (shiploc ?l)
        (shuttleloc ?s ?l)
        (personloc ?p ?r)
        (ship_broken)
        (transporter_broken)
        (lequipment ?e)
        (lequipmentloc ?e ?l)
    )
    
    (:action move_using_door
        :parameters (?p ?r1 ?r2)
        :precondition (and (person ?p) (location ?r1) (location ?r2) (personloc ?p ?r1) (door ?r1 ?r2))
        :effect (and (personloc ?p ?r2) (not (personloc ?p ?r1)))
    )
    (:action move_using_elevator
        :parameters (?p ?r1 ?r2)
        :precondition (and (person ?p) (location ?r1) (location ?r2) (personloc ?p ?r1) (elevator ?r1 ?r2))
        :effect (and (personloc ?p ?r2) (not (personloc ?p ?r1)))
    )
    (:action move_ship
        :parameters (?l1 ?l2)
        :precondition (and 
            (not(ship_broken))
            (planet ?l1) 
            (planet ?l2) 
            (not (= ?l1 ?l2)) 
            (shiploc ?l1)
            (personloc captain bridge)
            (personloc navigator bridge))
        :effect (and (shiploc ?l2) (not(shiploc ?l1)))
    ) 
    (:action teleport_to_planet
        :parameters (?p ?l)
        :precondition (and (shiploc ?l) (person ?p) (planet ?l) (personloc transport_cheif transporter) (personloc ?p transporter) (not (transporter_broken)))
        :effect (and (personloc ?p ?l) (not (personloc ?p transporter)))
    )
    (:action teleport_from_planet
        :parameters (?p ?l)
        :precondition (and (shiploc ?l) (person ?p) (planet ?l) (personloc transport_cheif transporter) (personloc ?p ?l) (not (transporter_broken)))
        :effect (and (not(personloc ?p ?l)) (personloc ?p transporter))
    )
    (:action fix_ship
        :precondition (and (personloc engineer engineering) (ship_broken))
        :effect (not (ship_broken))
    )
    (:action board_shuttle
        :parameters (?p ?s ?l)
        :precondition (and 
            (person ?p)
            (shuttle ?s)
            (location ?l)
            (shuttleloc ?s ?l)
            (personloc ?p ?l))
        :effect (and
            (personloc ?p ?s)
            (not (personloc ?p ?l))
        )
    )
    (:action alight_from_shuttle
        :parameters (?p ?s ?l)
        :precondition (and 
            (person ?p)
            (shuttle ?s)
            (location ?l)
            (shuttleloc ?s ?l)
            (personloc ?p ?s))
        :effect (and
            (personloc ?p ?l)
            (not (personloc ?p ?s))
        )
    )
    (:action send_shuttle_to_planet
        :parameters (?s ?p)
        :precondition (and 
            (shuttle ?s)
            (planet ?p)
            (shiploc ?p)
            (shuttleloc ?s shuttlebay))
        :effect (and
            (shuttleloc ?s ?p)
            (not (shuttleloc ?s shuttlebay))
        )
    )
    (:action return_shuttle_to_ship
        :parameters (?s ?p)
        :precondition (and 
            (shuttle ?s)
            (planet ?p)
            (shiploc ?p)
            (shuttleloc ?s ?p))
        :effect (and
            (shuttleloc ?s shuttlebay)
            (not (shuttleloc ?s ?p))
        )
    )    
    (:action move_light_equipment
        :parameters (?a ?b ?p ?e)
        :precondition (and 
            (location ?a)
            (location ?b)
            (not(= ?a ?b))
            (door ?a ?b)
            (person ?p)
            (lequipment ?e)
            (personloc ?p ?a)
            (lequipmentloc ?e ?a))
        :effect (and
            (lequipmentloc ?e ?b)
            (personloc ?p ?b)
            (not(lequipmentloc ?e ?a))
            (not(personloc ?e ?a))
            )
    )
    (:action move_light_equipment_elevator
        :parameters (?a ?b ?p ?e)
        :precondition (and 
            (location ?a)
            (location ?b)
            (not(= ?a ?b))
            (elevator ?a ?b)
            (person ?p)
            (lequipment ?e)
            (personloc ?p ?a)
            (lequipmentloc ?e ?a))
        :effect (and
            (lequipmentloc ?e ?b)
            (personloc ?p ?b)
            (not(lequipmentloc ?e ?a))
            (not(personloc ?e ?a))
            )
    )
)