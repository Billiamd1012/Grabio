package me.billdarker.ass1.world;

import com.badlogic.gdx.Gdx;

public class Attack {
    //This class is owned by territory and is created each time a territory attacks and object
    private final Territory attacker;
    private final Player defender;
    private float attackingTroops;
    public Attack(Territory _attacker, Player _defender, float _attackingTroops){
        attacker = _attacker;
        defender = _defender;
        attackingTroops = _attackingTroops;

        Gdx.app.log("Attack","Attack class created");
    }

    public void update(){
        //each update call defend on defender which takes away some troops
        attackingTroops = defender.defend(attackingTroops);
    }
}
