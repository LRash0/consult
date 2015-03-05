/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.entities.Consultant;
import jpa.entities.controller.exceptions.NonexistentEntityException;
import jpa.entities.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author lucas.freire
 */
public class ConsultantJpaController implements Serializable {

    public ConsultantJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consultant consultant) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(consultant);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConsultant(consultant.getConsultantId()) != null) {
                throw new PreexistingEntityException("Consultant " + consultant + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consultant consultant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            consultant = em.merge(consultant);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = consultant.getConsultantId();
                if (findConsultant(id) == null) {
                    throw new NonexistentEntityException("The consultant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consultant consultant;
            try {
                consultant = em.getReference(Consultant.class, id);
                consultant.getConsultantId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultant with id " + id + " no longer exists.", enfe);
            }
            em.remove(consultant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Consultant> findConsultantEntities() {
        return findConsultantEntities(true, -1, -1);
    }

    public List<Consultant> findConsultantEntities(int maxResults, int firstResult) {
        return findConsultantEntities(false, maxResults, firstResult);
    }

    private List<Consultant> findConsultantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consultant.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Consultant findConsultant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consultant.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consultant> rt = cq.from(Consultant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
